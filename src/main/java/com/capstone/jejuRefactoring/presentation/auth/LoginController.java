package com.capstone.jejuRefactoring.presentation.auth;



import static com.capstone.jejuRefactoring.config.security.provider.JwtTokenProvider.*;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.jejuRefactoring.application.auth.AuthFacade;
import com.capstone.jejuRefactoring.common.exception.CommonResponse;
import com.capstone.jejuRefactoring.common.exception.security.NotExistsRefreshTokenException;
import com.capstone.jejuRefactoring.domain.auth.Member;
import com.capstone.jejuRefactoring.domain.auth.dto.response.AuthResponse;
import com.capstone.jejuRefactoring.presentation.auth.dto.request.JoinRequest;
import com.capstone.jejuRefactoring.config.security.provider.JwtTokenProvider;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class LoginController {

	private final AuthFacade authFacade;
	private final JwtTokenProvider jwtTokenProvider;

	@PostMapping("/join")
	public ResponseEntity<CommonResponse> join(@Valid @RequestBody final JoinRequest joinRequest) {
		authFacade.join(joinRequest);
		return ResponseEntity.ok()
			.body(CommonResponse.success("회원가입 성공"));
	}

	@GetMapping("/logout")
	public ResponseEntity<CommonResponse> logout(@RequestHeader(JwtTokenProvider.ACCESS_TOKEN) String accessToken,
		@CookieValue(value = REFRESH_TOKEN, required = false) final String refreshToken) {
		authFacade.logout(accessToken, refreshToken);
		validateRefreshTokenExists(refreshToken);
		return ResponseEntity.ok()
			.body(CommonResponse.success("로그아웃 성공"));
	}

	@GetMapping("/deleteUser")
	public ResponseEntity<CommonResponse> deleteUser(@RequestHeader(JwtTokenProvider.ACCESS_TOKEN) String accessToken,
		@CookieValue(value = REFRESH_TOKEN, required = false) final String refreshToken,
		@LoginUser Member member) {
		authFacade.deleteMember(member, accessToken, refreshToken);
		return ResponseEntity.ok()
			.body(CommonResponse.success("회원 탈퇴 성공"));
	}

	@PostMapping("/reissue")
	public ResponseEntity<CommonResponse<AuthResponse>> reissueToken(
		@CookieValue(value = REFRESH_TOKEN, required = false) final String refreshToken) {
		validateRefreshTokenExists(refreshToken);
		AuthResponse authResponse = AuthResponse.from(authFacade.reissue(refreshToken));
		ResponseCookie responseCookie = jwtTokenProvider.createCookie(authResponse.getRefreshToken());

		return ResponseEntity.ok()
			.header(HttpHeaders.SET_COOKIE, responseCookie.toString())
			.body(CommonResponse.success(authResponse));
	}

	private void validateRefreshTokenExists(final String refreshToken) {
		if (refreshToken == null) {
			throw new NotExistsRefreshTokenException();
		}
	}

}










