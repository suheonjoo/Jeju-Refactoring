package com.capstone.jejuRefactoring.unit.domain.auth.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.capstone.jejuRefactoring.domain.auth.LogoutAccessToken;

public class LogoutAccessTokenTest {

	@Test
	void LogoutAccessToken_정적_메서드_생성() throws Exception {
		//given
		String accessToken = "accessToken";
		long expiration = 3600000;

		//when
		LogoutAccessToken logoutAccessToken = LogoutAccessToken.of(accessToken, expiration);

		//then
		assertThat(logoutAccessToken.getId()).isEqualTo(accessToken);
		assertThat(logoutAccessToken.getExpiration()).isEqualTo(expiration);
	}
}
