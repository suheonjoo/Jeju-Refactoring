package com.capstone.jejuRefactoring.support.helper;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.capstone.jejuRefactoring.config.security.dto.AccountContext;
import com.capstone.jejuRefactoring.domain.auth.Member;
import com.capstone.jejuRefactoring.domain.auth.Role;

public class MemberGivenHelper {

	public static Member givenMember() {
		return Member.builder()
			.email("suheon95@gmail.com")
			.username("bluelaw")
			.password("1111")
			.role(Role.USER)
			.build();
	}

	public static Member givenUserIncludeInfo() {
		return Member.builder()
			.email("suheon95@gmail.com")
			.username("bluelaw")
			.role(Role.USER)
			.build();
	}

	public static Authentication createAuthentication(Member member) {
		// UserDetails userDetails = userDetailsService.loadUserByUsername(member.getEmail());
		List<SimpleGrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority(member.getRole()));
		UserDetails userDetails = new AccountContext(member, authorities);
		return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null,
			userDetails.getAuthorities());
	}

}
