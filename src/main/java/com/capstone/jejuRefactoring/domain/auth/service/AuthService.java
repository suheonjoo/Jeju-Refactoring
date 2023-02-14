package com.capstone.jejuRefactoring.domain.auth.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capstone.jejuRefactoring.common.exception.security.NotExistsRefreshTokenException;
import com.capstone.jejuRefactoring.common.exception.user.UserNotFoundException;
import com.capstone.jejuRefactoring.domain.auth.LogoutAccessToken;
import com.capstone.jejuRefactoring.domain.auth.LogoutRefreshToken;
import com.capstone.jejuRefactoring.domain.auth.Member;
import com.capstone.jejuRefactoring.domain.auth.RefreshToken;
import com.capstone.jejuRefactoring.domain.auth.Role;
import com.capstone.jejuRefactoring.domain.auth.dto.AuthResponseDto;
import com.capstone.jejuRefactoring.domain.auth.dto.LoginResult;
import com.capstone.jejuRefactoring.domain.auth.repository.TokenRepository;
import com.capstone.jejuRefactoring.infrastructure.auth.respository.MemberJpaRepository;
import com.capstone.jejuRefactoring.presentation.auth.dto.request.JoinRequest;
import com.capstone.jejuRefactoring.config.security.provider.JwtExpirationEnums;
import com.capstone.jejuRefactoring.config.security.provider.JwtTokenProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService implements AuthCommandUseCase {

	private final MemberJpaRepository memberJpaRepository;
	private final PasswordEncoder passwordEncoder;
	private final TokenRepository tokenRepository;

	private final JwtTokenProvider jwtTokenProvider;

	@Transactional
	@Override
	public Long join(JoinRequest joinRequest) {
		validateDuplicateMember(joinRequest.getEmail());
		String encodedPassword = passwordEncoder.encode(joinRequest.getPassword());
		Member member = Member.builder()
			.email(joinRequest.getEmail())
			.username(joinRequest.getUsername())
			.password(encodedPassword)
			.role(Role.USER)
			.build();
		memberJpaRepository.save(member);
		return memberJpaRepository.findOptionByEmail(joinRequest.getEmail())
			.orElseThrow(() -> new UserNotFoundException()).getId();
	}

	public LoginResult login(final String email) {
		Member member = memberJpaRepository.findOptionByEmail(email)
			.orElseThrow(() -> new UserNotFoundException());
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String accessToken = jwtTokenProvider.createAccessToken(authentication);
		String refreshToken = jwtTokenProvider.createRefreshToken(authentication);
		return new LoginResult(refreshToken, accessToken, member);
	}

	@Transactional
	@Override
	public void logout(String accessToken, String refreshToken) {
		saveLogoutAccessToken(accessToken);
		saveLogoutRefreshToken(refreshToken);
	}

	private void saveLogoutAccessToken(String accessToken) {
		String removedTypeAccessToken = getRemovedBearerType(accessToken);
		LogoutAccessToken logoutAccessToken = LogoutAccessToken.of(removedTypeAccessToken,
			getRemainingMilliSecondsFromToken(removedTypeAccessToken));
		tokenRepository.saveLogoutAccessToken(logoutAccessToken);
	}

	private void saveLogoutRefreshToken(String refreshToken) {
		String removedTypeRefreshToken = getRemovedBearerType(refreshToken);
		LogoutRefreshToken logoutRefreshToken = LogoutRefreshToken.of(removedTypeRefreshToken,
			getRemainingMilliSecondsFromToken(removedTypeRefreshToken));
		tokenRepository.saveLogoutRefreshToken(logoutRefreshToken);
	}

	private String getRemovedBearerType(String token) {
		return token.substring(7);
	}

	private long getRemainingMilliSecondsFromToken(String token) {
		return jwtTokenProvider.getRemainingMilliSecondsFromToken(token);
	}

	@Transactional
	@Override
	public void deleteMember(Member member, String accessToken, String refreshToken) {
		memberJpaRepository.delete(member);
		deleteOriginRefreshToken(refreshToken);
		LogoutAccessToken logoutAccessToken = LogoutAccessToken.of(accessToken,
			JwtExpirationEnums.ACCESS_TOKEN_EXPIRATION_TIME.getValue());
		tokenRepository.saveLogoutAccessToken(logoutAccessToken);
	}

	private void deleteOriginRefreshToken(String refreshToken) {
		tokenRepository.deleteRefreshTokenById(refreshToken);
		tokenRepository.saveLogoutRefreshToken(
			LogoutRefreshToken.of(refreshToken, getRemainingMilliSecondsFromToken(refreshToken)));
	}

	@Transactional
	@Override
	public AuthResponseDto reissue(String refreshToken) {
		refreshToken = jwtTokenProvider.removeType(refreshToken);
		isInRedisOrThrow(refreshToken);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String newAccessToken = jwtTokenProvider.createAccessToken(authentication);
		if (jwtTokenProvider.isMoreThanReissueTime(refreshToken))
			return AuthResponseDto.of(newAccessToken, refreshToken);

		deleteOriginRefreshToken(refreshToken);
		String newRefreshToken = createNewRefreshToken(authentication);
		return AuthResponseDto.of(newAccessToken, newRefreshToken);
	}

	private String createNewRefreshToken(Authentication authentication) {
		String newRefreshToken = jwtTokenProvider.createRefreshToken(authentication);
		tokenRepository.saveRefreshToken(
			RefreshToken.of(newRefreshToken, getRemainingMilliSecondsFromToken(newRefreshToken)));
		return newRefreshToken;
	}

	private void isInRedisOrThrow(String refreshToken) {
		if (!tokenRepository.existsRefreshTokenById(refreshToken)) {
			throw new NotExistsRefreshTokenException();
		}
	}

	//회원 중복 검사
	private void validateDuplicateMember(String email) {
		memberJpaRepository.findOptionByEmail(email)
			.ifPresent(member -> {
				throw new UserNotFoundException();
			});
	}

}























