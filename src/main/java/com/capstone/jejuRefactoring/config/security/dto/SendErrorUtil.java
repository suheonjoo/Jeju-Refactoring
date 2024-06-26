package com.capstone.jejuRefactoring.config.security.dto;

import java.io.IOException;

import org.springframework.http.HttpStatus;

import com.capstone.jejuRefactoring.common.exception.CommonResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletResponse;

public class SendErrorUtil {

	private static final String UNAUTHORIZED_MESSAGE = "유효하지 않은 토큰입니다.";
	private static final String UNAUTHORIZED_CODE = "LOGIN-401";
	private static final String SERVER_ERROR_MESSAGE = "서버에 문제가 발생했습니다.";
	private static final String SERVER_ERROR_CODE = "SERVER-500";

	public static void sendUnauthorizedErrorResponse(HttpServletResponse response, ObjectMapper objectMapper) throws
		IOException {
		String errorResponse = objectMapper.writeValueAsString(
			CommonResponse.failOf(UNAUTHORIZED_MESSAGE, UNAUTHORIZED_CODE));
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		writeErrorResponse(response, errorResponse);
	}

	public static void sendServerErrorResponse(HttpServletResponse response, ObjectMapper objectMapper) throws
		IOException {
		String errorResponse = objectMapper.writeValueAsString(
			CommonResponse.failOf(SERVER_ERROR_MESSAGE, SERVER_ERROR_CODE));
		response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		writeErrorResponse(response, errorResponse);
	}

	private static void writeErrorResponse(HttpServletResponse response, String errorResponse) throws IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		response.getWriter().write(errorResponse);
	}

}
