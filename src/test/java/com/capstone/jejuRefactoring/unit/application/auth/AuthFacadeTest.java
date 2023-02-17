package com.capstone.jejuRefactoring.unit.application.auth;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.capstone.jejuRefactoring.application.auth.AuthFacade;
import com.capstone.jejuRefactoring.domain.auth.dto.AuthResponseDto;
import com.capstone.jejuRefactoring.domain.auth.service.AuthCommandUseCase;

@ExtendWith(MockitoExtension.class)
public class AuthFacadeTest {

	@InjectMocks
	AuthFacade authFacade;
	@Mock
	AuthCommandUseCase authCommandUseCase;

	@Test
	void 토큰_재발급() {
		//given
		String refreshToken= "Bearer refresh";
		String accessToken = "Bearer access";
		AuthResponseDto authResponseDto = AuthResponseDto.of(accessToken, refreshToken);
		given(authCommandUseCase.reissue(any())).willReturn(authResponseDto);

		//when
		AuthResponseDto result = authFacade.reissue(refreshToken);

		//then
		assertThat(result.getAccessToken()).isEqualTo(authResponseDto.getAccessToken());
		assertThat(result.getRefreshToken()).isEqualTo(authResponseDto.getRefreshToken());
		assertThat(result.getTokenType()).isEqualTo(authResponseDto.getTokenType());
	}
}
