package com.capstone.jejuRefactoring.support;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import com.capstone.jejuRefactoring.application.auth.AuthFacade;
import com.capstone.jejuRefactoring.application.picture.PictureFacade;
import com.capstone.jejuRefactoring.application.preference.PreferenceFacade;
import com.capstone.jejuRefactoring.application.review.ReviewFacade;
import com.capstone.jejuRefactoring.application.spot.SpotFacade;
import com.capstone.jejuRefactoring.application.wishList.WishListFacade;
import com.capstone.jejuRefactoring.config.SecurityTestConfig;
import com.capstone.jejuRefactoring.config.security.provider.JwtTokenProvider;
import com.capstone.jejuRefactoring.domain.auth.Member;
import com.capstone.jejuRefactoring.domain.auth.Role;
import com.capstone.jejuRefactoring.domain.auth.repository.TokenRepository;
import com.capstone.jejuRefactoring.infrastructure.auth.respository.MemberJpaRepository;
import com.capstone.jejuRefactoring.integration.review.ReviewIntegrationTest;
import com.capstone.jejuRefactoring.unit.presentation.auth.LoginControllerTest;
import com.capstone.jejuRefactoring.unit.presentation.picture.PictureControllerTest;
import com.capstone.jejuRefactoring.unit.presentation.preference.PreferenceControllerTest;
import com.capstone.jejuRefactoring.unit.presentation.spot.SpotControllerTest;
import com.capstone.jejuRefactoring.unit.presentation.wishList.WishListControllerTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Import(SecurityTestConfig.class)
@WebMvcTest({
	LoginControllerTest.class,
	PictureControllerTest.class,
	PreferenceControllerTest.class,
	ReviewIntegrationTest.class,
	SpotControllerTest.class,
	WishListControllerTest.class
})
public abstract class ControllerTest {

	protected MockMvc mockMvc;

	@Autowired
	protected ObjectMapper objectMapper;
	@MockBean
	protected AuthFacade authFacade;
	@MockBean protected PictureFacade diseaseTagFacade;
	@MockBean protected PreferenceFacade calendarFacade;
	@MockBean protected ReviewFacade userFacade;
	@MockBean protected SpotFacade diseaseFacade;
	@MockBean protected WishListFacade diagnosisFacade;

	// security
	@MockBean protected JwtTokenProvider jwtTokenProvider;
	@MockBean protected MemberJpaRepository memberRepository;
	@MockBean protected TokenRepository tokenRepository;

	protected String createJson(Object dto) throws JsonProcessingException {
		return objectMapper.writeValueAsString(dto);
	}

	protected void mockingSecurityFilterForLoginUserAnnotation(){
		given(jwtTokenProvider.validateToken(any())).willReturn(true);
		given(tokenRepository.existsLogoutAccessTokenById(any())).willReturn(false);
		given(tokenRepository.existsLogoutRefreshTokenById(any())).willReturn(false);
		given(memberRepository.findOptionByEmail(any())).willReturn(Optional.of(Member.builder()
			.id(1L)
			.email("tngjs95@gmail.com")
			.username("주수헌")
			.role(Role.USER)
			.build()));
	}
}

