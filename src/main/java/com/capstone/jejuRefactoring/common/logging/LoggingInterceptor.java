package com.capstone.jejuRefactoring.common.support.logging;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.capstone.jejuRefactoring.common.logging.ApiQueryCounter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LoggingInterceptor implements HandlerInterceptor {

	private static final String QUERY_COUNT_LOG_FORMAT = "STATUS_CODE: {}, METHOD: {}, URL: {}, QUERY_COUNT: {}";
	private static final String QUERY_COUNT_WARNING_LOG_FORMAT = "쿼리가 {}번 이상 실행되었습니다.";

	private static final int QUERY_COUNT_WARNING_STANDARD = 10;

	private final ApiQueryCounter apiQueryCounter;

	public LoggingInterceptor(final ApiQueryCounter apiQueryCounter) {
		this.apiQueryCounter = apiQueryCounter;
	}

	@Override
	public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response,
		final Object handler, final Exception ex) {
		final int queryCount = apiQueryCounter.getCount();

		System.out.println("=========================");

		log.info(QUERY_COUNT_LOG_FORMAT, response.getStatus(), request.getMethod(), request.getRequestURI(),
			queryCount);
		if (queryCount >= QUERY_COUNT_WARNING_STANDARD) {
			log.warn(QUERY_COUNT_WARNING_LOG_FORMAT, QUERY_COUNT_WARNING_STANDARD);
		}
	}
}