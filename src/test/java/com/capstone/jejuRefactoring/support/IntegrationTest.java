package com.capstone.jejuRefactoring.support;

import static com.capstone.jejuRefactoring.support.helper.MemberGivenHelper.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.transaction.annotation.Transactional;

import com.capstone.jejuRefactoring.config.security.provider.JwtTokenProvider;
import com.capstone.jejuRefactoring.domain.auth.Member;
import com.capstone.jejuRefactoring.infrastructure.auth.respository.LogoutAccessTokenRedisRepository;
import com.capstone.jejuRefactoring.infrastructure.auth.respository.LogoutRefreshTokenRedisRepository;
import com.capstone.jejuRefactoring.infrastructure.auth.respository.MemberJpaRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.EntityManager;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class IntegrationTest {

	@Autowired
	protected MockMvc mockMvc;
	@Autowired protected ObjectMapper objectMapper;
	@Autowired protected EntityManager em;

	@Autowired protected JwtTokenProvider tokenProvider;
	@Autowired
	MemberJpaRepository memberJpaRepository;
	@Autowired
	LogoutAccessTokenRedisRepository logoutAccessTokenRedisRepository;
	@Autowired
	LogoutRefreshTokenRedisRepository logoutRefreshTokenRedisRepository;
	@Autowired
	UserDetailsService userDetailsService;


	@BeforeEach
	void clearUser(){
		memberJpaRepository.deleteAllInBatch();
		logoutAccessTokenRedisRepository.deleteAll();
		logoutRefreshTokenRedisRepository.deleteAll();
	}

	protected String createJson(Object dto) throws JsonProcessingException {
		return objectMapper.writeValueAsString(dto);
	}

	protected String createAccessToken() {
		Member member = memberJpaRepository.save(givenMember());
		Authentication authentication = createAuthentication(member);
		return tokenProvider.createAccessToken(authentication);
	}

	protected String createAccessToken(Member member) {
		Authentication authentication = createAuthentication(member);
		return tokenProvider.createAccessToken(authentication);
	}

	private Authentication createAuthentication(Member member) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(member.getEmail());
		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null,
			userDetails.getAuthorities());
		return authentication;
	}

	protected void flushAndClear(){
		em.flush();
		em.clear();
	}

	protected ResultMatcher[] expectCommonSuccess() {
		return new ResultMatcher[]{jsonPath("$.result", is("SUCCESS")),
			jsonPath("$.message").isEmpty(),
			jsonPath("$.code").isEmpty(),
			jsonPath("$.errors").isEmpty()};
	}

}
