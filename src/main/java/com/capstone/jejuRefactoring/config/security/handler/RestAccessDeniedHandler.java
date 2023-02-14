package com.capstone.jejuRefactoring.config.security.handler;

import java.io.IOException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RestAccessDeniedHandler implements AccessDeniedHandler {

	private final String MESSAGE = "권한이 없습니다.";

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
		AccessDeniedException accessDeniedException)
		throws IOException {
		response.sendError(HttpServletResponse.SC_FORBIDDEN, MESSAGE);
	}
}
