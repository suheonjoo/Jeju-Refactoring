package com.capstone.jejuRefactoring.unit.domain.auth.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.capstone.jejuRefactoring.common.exception.security.NotExistsRefreshTokenException;
import com.capstone.jejuRefactoring.config.security.provider.JwtTokenProvider;
import com.capstone.jejuRefactoring.domain.auth.dto.AuthResponseDto;
import com.capstone.jejuRefactoring.domain.auth.repository.TokenRepository;
import com.capstone.jejuRefactoring.domain.auth.service.AuthService;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

	@InjectMocks
	AuthService authService;
	@Mock
	JwtTokenProvider tokenProvider;
	@Mock
	TokenRepository tokenRepository;

	@Test
	void 로그아웃() throws Exception {
		//given
		String accessToken = "Bearer accessToken";
		String refreshToken = "Bearer accessToken";

		//when
		authService.logout(accessToken, refreshToken);

		//then
		then(tokenRepository).should().saveLogoutAccessToken(any());
		then(tokenRepository).should().saveLogoutRefreshToken(any());
	}

	@Test
	void 토큰이_redis에_없는_경우() throws Exception {
		//given
		String refreshToken = "refreshToken";
		given(tokenRepository.existsRefreshTokenById(any())).willReturn(false);

		//when
		assertThatThrownBy(() -> authService.reissue(refreshToken))
			.isInstanceOf(NotExistsRefreshTokenException.class);
	}

	@Test
	void Refresh토큰이_아직_reissue_time이_남아있는_경우() throws Exception {
		//given
		String refreshToken = "refreshToken";
		String accessToken = "accessToken";
		given(tokenProvider.removeType(any())).willReturn(refreshToken);
		given(tokenRepository.existsRefreshTokenById(any())).willReturn(true);
		given(tokenProvider.createAccessToken(any())).willReturn(accessToken);
		given(tokenProvider.isMoreThanReissueTime(any())).willReturn(true);

		//when
		AuthResponseDto result = authService.reissue(refreshToken);

		// then
		assertThat(result.getAccessToken()).isEqualTo(accessToken);
		assertThat(result.getRefreshToken()).isEqualTo(refreshToken);
		assertThat(result.getTokenType()).isEqualTo("Bearer");
	}

	@Test
	void Refresh토큰이_reissue_time보다_적게_남은_경우() throws Exception {
		//given
		String refreshToken = "refreshToken";
		String accessToken = "accessToken";
		String newRefreshToken = "newRefreshTokenk";
		given(tokenRepository.existsRefreshTokenById(any())).willReturn(true);
		given(tokenProvider.createAccessToken(any())).willReturn(accessToken);
		given(tokenProvider.isMoreThanReissueTime(any())).willReturn(false);
		given(tokenProvider.createRefreshToken(any())).willReturn(newRefreshToken);

		//when
		AuthResponseDto result = authService.reissue(refreshToken);

		// then
		assertThat(result.getAccessToken()).isEqualTo(accessToken);
		assertThat(result.getRefreshToken()).isEqualTo(newRefreshToken);
		assertThat(result.getTokenType()).isEqualTo("Bearer");
	}
}

