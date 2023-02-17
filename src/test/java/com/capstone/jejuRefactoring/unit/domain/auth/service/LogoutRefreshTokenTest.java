package com.capstone.jejuRefactoring.unit.domain.auth.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.capstone.jejuRefactoring.domain.auth.LogoutAccessToken;

public class LogoutRefreshTokenTest {

	@Test
	void LogoutRefreshToken_정적_메서드_생성() throws Exception {
		//given
		String refreshToken = "refreshToken";
		long expiration = 86400000;

		//when
		LogoutAccessToken logoutAccessToken = LogoutAccessToken.of(refreshToken, expiration);

		//then
		assertThat(logoutAccessToken.getId()).isEqualTo(refreshToken);
		assertThat(logoutAccessToken.getExpiration()).isEqualTo(expiration);
	}
}
