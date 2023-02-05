package com.capstone.jejuRefactoring.security.handler;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.capstone.jejuRefactoring.common.exception.CommonResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LoginFailureHandler implements AuthenticationFailureHandler {

	private final ObjectMapper objectMapper;

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException exception) throws IOException {
		String errorMessage = "Invalid Username or Password";
		if (exception instanceof BadCredentialsException) {
			errorMessage = "Invalid Username or Password";
		} else if (exception instanceof DisabledException) {
			errorMessage = "Locked";
		} else if (exception instanceof CredentialsExpiredException) {
			errorMessage = "Expired password";
		}
		setHttpResponseOption(response, errorMessage);
	}

	private void setHttpResponseOption(HttpServletResponse response, String errorMessage) throws IOException {
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.writeValue(response.getWriter(),
			CommonResponse.failOf(errorMessage, HttpStatus.UNAUTHORIZED.toString()));
	}
}














