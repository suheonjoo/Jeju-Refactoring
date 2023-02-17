package com.capstone.jejuRefactoring.unit.infrastructure.auth;

import static com.capstone.jejuRefactoring.support.helper.MemberGivenHelper.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.capstone.jejuRefactoring.config.security.provider.JwtTokenProvider;
import com.capstone.jejuRefactoring.domain.auth.Member;

public class JwtTokenProviderTest {

	JwtTokenProvider jwtTokenProvider;

	@Autowired

	@BeforeEach
	void setup() {
		jwtTokenProvider = new JwtTokenProvider("secret", 21600000L, 259200000L, 604800000L);
	}

	// private Authentication createAuthentication(Member member) {
	//     // UserDetails userDetails = userDetailsService.loadUserByUsername(member.getEmail());
	//     List<SimpleGrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority(member.getRole()));
	//     UserDetails userDetails = new AccountContext(member, authorities);
	//     return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null,
	//         userDetails.getAuthorities());
	// }

	@Test
	void accessToken_만들기() throws Exception {
		//given
		Member member = givenMember();

		//when
		String accessToken = getAccessToken(member);

		//then
		String userEmailFromToken = jwtTokenProvider.getUserPk(accessToken);
		assertThat(userEmailFromToken).isEqualTo(member.getEmail());
	}

	@Test
	void refreshToken_만들기() throws Exception {
		//given
		Member member = givenMember();

		//when
		String refreshToken = jwtTokenProvider.createRefreshToken(createAuthentication(member));

		//then
		String userEmailFromToken = jwtTokenProvider.getUserPk(refreshToken);
		assertThat(userEmailFromToken).isEqualTo(member.getEmail());
	}

	@Test
	void 토큰에서_이메일_추출하기() throws Exception {
		//given
		Member member = givenMember();
		String accessToken = getAccessToken(member);

		//when
		String userEmailFromToken = jwtTokenProvider.getUserPk(accessToken);

		//then
		assertThat(userEmailFromToken).isEqualTo(member.getEmail());
	}

	@Test
	void 토큰_남은_시간_추출하기() throws Exception {
		//given
		String accessToken = getAccessToken(givenMember());

		//when
		long remainingMilliSecondsFromToken = jwtTokenProvider.getRemainingMilliSecondsFromToken(accessToken);

		//then
		assertThat(remainingMilliSecondsFromToken).isLessThan(21600000L);
	}

	@Test
	void 토큰_유효성_체크_성공() throws Exception {
		//given
		String accessToken = getAccessToken(givenMember());

		//when
		boolean result = jwtTokenProvider.validateToken(accessToken);

		// then
		assertThat(result).isTrue();
	}

	@Test
	void secret이_다른_토큰_유효성_체크() throws Exception {
		//given
		String accessToken = getAccessToken(givenMember());
		JwtTokenProvider anotherJWTTokenProvider = new JwtTokenProvider("new_secret", 10000L, 10000L, 10000L);

		//when
		boolean result = anotherJWTTokenProvider.validateToken(accessToken);

		// then
		assertThat(result).isFalse();
	}

	private String getAccessToken(Member member) {
		return jwtTokenProvider.createAccessToken(createAuthentication(member));
	}

	@Test
	void 구조적_문제가_있는_토큰_유효성_체크() throws Exception {
		//given
		String accessToken = "dummy";

		//when
		boolean result = jwtTokenProvider.validateToken(accessToken);

		// then
		assertThat(result).isFalse();
	}

	@Test
	void 만료된_토큰_유효성_체크() throws Exception {
		//given
		jwtTokenProvider = new JwtTokenProvider("secret", 0L, 0L, 0L);
		String accessToken = jwtTokenProvider.createAccessToken(createAuthentication(givenMember()));

		//when
		boolean result = jwtTokenProvider.validateToken(accessToken);

		// then
		assertThat(result).isFalse();
	}

	@Test
	void refreshToken_유효시간이_재발급_기간보다_적게_남은_경우() {
		//given
		jwtTokenProvider = new JwtTokenProvider("secret", 1000L, 1000L, 1000L);
		String refreshToken = jwtTokenProvider.createRefreshToken(createAuthentication(givenMember()));

		//when
		boolean result = jwtTokenProvider.isMoreThanReissueTime(refreshToken);

		//then
		assertThat(result).isFalse();
	}

	@Test
	void refreshToken_유효시간이_재발급_기간보다_많이_남은_경우() {
		//given
		jwtTokenProvider = new JwtTokenProvider("secret", 1000L, 100000L, 1000L);
		String refreshToken = jwtTokenProvider.createRefreshToken(createAuthentication(givenMember()));

		//when
		boolean result = jwtTokenProvider.isMoreThanReissueTime(refreshToken);

		//then
		assertThat(result).isTrue();
	}

}
