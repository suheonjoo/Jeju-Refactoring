package com.capstone.jejuRefactoring.config.security.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StreamUtils;

import com.capstone.jejuRefactoring.presentation.auth.dto.request.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoginProcessingAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	public LoginProcessingAuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
		super(requiresAuthenticationRequestMatcher);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws
		AuthenticationException, IOException {
		LoginRequest loginRequest = getLoginForm(request);
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
			loginRequest.getEmail(), loginRequest.getPassword());
		return super.getAuthenticationManager().authenticate(authRequest);
	}

	private LoginRequest getLoginForm(HttpServletRequest request) throws IOException {
		ServletInputStream inputStream = request.getInputStream();
		String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(messageBody, LoginRequest.class);
	}

}
