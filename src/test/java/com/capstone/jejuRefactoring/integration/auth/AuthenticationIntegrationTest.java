package com.capstone.jejuRefactoring.integration.auth;

import static com.capstone.jejuRefactoring.config.security.provider.JwtTokenProvider.*;
import static com.capstone.jejuRefactoring.support.helper.MemberGivenHelper.*;
import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Iterator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.capstone.jejuRefactoring.config.security.provider.JwtTokenProvider;
import com.capstone.jejuRefactoring.domain.auth.Member;
import com.capstone.jejuRefactoring.domain.auth.RefreshToken;
import com.capstone.jejuRefactoring.domain.auth.repository.TokenRepository;
import com.capstone.jejuRefactoring.infrastructure.auth.respository.LogoutRefreshTokenRedisRepository;
import com.capstone.jejuRefactoring.infrastructure.auth.respository.MemberJpaRepository;
import com.capstone.jejuRefactoring.infrastructure.auth.respository.RefreshTokenRedisRepository;
import com.capstone.jejuRefactoring.support.IntegrationTest;

import jakarta.servlet.http.Cookie;

public class AuthenticationIntegrationTest extends IntegrationTest {

	private final String URL = "/api/v1/";
	private final String TOKEN_TYPE = "Bearer ";

	@Autowired
	MemberJpaRepository memberJpaRepository;
	@Autowired
	TokenRepository tokenRepository;
	@Autowired
	RefreshTokenRedisRepository refreshTokenRedisRepository;
	@Autowired
	LogoutRefreshTokenRedisRepository logoutRefreshTokenRedisRepository;

	@Test
	void 로그아웃() throws Exception {

		//given
		Member member = memberJpaRepository.save(givenMember());

		Authentication authentication = createAuthentication(member);

		String accessToken = tokenProvider.createAccessToken(authentication);
		String refreshToken = tokenProvider.createRefreshToken(authentication);
		ResponseCookie cookie = tokenProvider.createCookie(refreshToken);


		//when then
		mockMvc.perform(MockMvcRequestBuilders.get(URL + "logout")
				.header(ACCESS_TOKEN, TOKEN_TYPE + accessToken)
				.cookie(new Cookie(REFRESH_TOKEN, TOKEN_TYPE + refreshToken))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
		;
	}


	@Test
	void Refresh토큰이_아직_reissue_time이_남아있는_경우_토큰_재발급() throws Exception {
		//given
		Member member = memberJpaRepository.save(givenMember());
		Authentication authentication = createAuthentication(member);
		String refreshToken = tokenProvider.createRefreshToken(authentication);
		tokenRepository.saveRefreshToken(
			RefreshToken.of(refreshToken, tokenProvider.getRemainingMilliSecondsFromToken(refreshToken)));

		//when then
		mockMvc.perform(MockMvcRequestBuilders.post(URL + "reissue")
				.cookie(new Cookie(REFRESH_TOKEN, TOKEN_TYPE + refreshToken))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.tokenType", is(TOKEN_TYPE.trim())))
			.andExpect(jsonPath("$.data.accessToken").isNotEmpty())
			.andExpect(jsonPath("$.data.refreshToken").isNotEmpty())
			.andExpectAll(
				expectCommonSuccess()
			)
		;
		assertThat(tokenRepository.existsRefreshTokenById(refreshToken)).isTrue();
	}

	@Test
	void Refresh토큰이_reissue_time보다_적게_남은_경우_토큰_재발급() throws Exception {
		//given
		refreshTokenRedisRepository.deleteAll();
		Member member = memberJpaRepository.save(givenMember());
		Authentication authentication = createAuthentication(member);
		JwtTokenProvider stubJWTTokenProvider = new JwtTokenProvider("test", 21600000L, 259200000L, 259200000L);
		String refreshToken = stubJWTTokenProvider.createRefreshToken(authentication);
		tokenRepository.saveRefreshToken(
			RefreshToken.of(refreshToken, stubJWTTokenProvider.getRemainingMilliSecondsFromToken(refreshToken)));

		//when then
		mockMvc.perform(MockMvcRequestBuilders.post(URL + "reissue")
				.cookie(new Cookie(REFRESH_TOKEN, TOKEN_TYPE + refreshToken))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.tokenType", is(TOKEN_TYPE.trim())))
			.andExpect(jsonPath("$.data.accessToken").isNotEmpty())
			.andExpect(jsonPath("$.data.refreshToken").isNotEmpty())
			.andExpectAll(
				expectCommonSuccess()
			)
		;

		assertThat(tokenRepository.existsRefreshTokenById(refreshToken)).isFalse();
		assertThat(logoutRefreshTokenRedisRepository.existsById(refreshToken)).isTrue();

		Iterator<RefreshToken> iterator = refreshTokenRedisRepository.findAll().iterator();
		int cnt = 0;
		while (iterator.hasNext()) {
			iterator.next();
			cnt += 1;
		}
		assertThat(cnt).isEqualTo(1);

	}

}

