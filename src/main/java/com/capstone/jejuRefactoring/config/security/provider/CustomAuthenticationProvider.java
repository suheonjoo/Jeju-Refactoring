package com.capstone.jejuRefactoring.config.security.provider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.capstone.jejuRefactoring.config.security.service.UserDetailServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Component
@Slf4j
public class CustomAuthenticationProvider implements AuthenticationProvider {

	private final UserDetailServiceImpl userDetailServiceImpl;
	private final PasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String password = (String)authentication.getCredentials();
		UserDetails userDetails = userDetailServiceImpl.loadUserByUsername(username);
		if (!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new BadCredentialsException("BadCredentialsException");//비밀번호가 틀린거
		}
		UsernamePasswordAuthenticationToken authenticationToken
			= new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
		return authenticationToken;
	}

	@Override
	public boolean supports(Class<?> aClass) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(aClass);
	}
}
