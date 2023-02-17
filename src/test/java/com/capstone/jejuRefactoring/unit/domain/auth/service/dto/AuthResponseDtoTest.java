package com.capstone.jejuRefactoring.unit.domain.auth.service.dto;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.capstone.jejuRefactoring.domain.auth.dto.AuthResponseDto;

class AuthResponseDtoTest {

	@Test
	void authResponseDto_from_정적_메서드로_생성() throws Exception {
		//given
		String accessToken = "accessToken";
		String refreshToken = "refreshToken";

		//when
		AuthResponseDto authResponseDto = AuthResponseDto.of(accessToken, refreshToken);

		//then
		assertThat(authResponseDto.getTokenType()).isEqualTo("Bearer");
		assertThat(authResponseDto.getAccessToken()).isEqualTo(accessToken);
		assertThat(authResponseDto.getRefreshToken()).isEqualTo(refreshToken);
	}

}