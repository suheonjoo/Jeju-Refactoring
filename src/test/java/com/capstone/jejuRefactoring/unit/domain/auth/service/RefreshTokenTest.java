package com.capstone.jejuRefactoring.unit.domain.auth.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.capstone.jejuRefactoring.domain.auth.RefreshToken;

public class RefreshTokenTest {

	@Test
	void LogoutRefreshToken_정적_메서드_생성() throws Exception {
		//given
		String refreshToken = "refreshToken";
		long expiration = 86400000;

		//when
		RefreshToken result = RefreshToken.of(refreshToken, expiration);

		//then
		assertThat(result.getId()).isEqualTo(refreshToken);
		assertThat(result.getExpiration()).isEqualTo(expiration);
	}
}
