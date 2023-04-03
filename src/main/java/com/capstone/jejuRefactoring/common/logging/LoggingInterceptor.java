package com.capstone.jejuRefactoring.common.support.logging;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.capstone.jejuRefactoring.common.logging.ApiQueryInfo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class LoggingInterceptor implements HandlerInterceptor {
	private static final String QUERY_COUNT_WARNING_LOG_FORMAT = "쿼리가 {}번 이상 실행되었습니다.";

	private static final int QUERY_COUNT_WARNING_STANDARD = 10;

	private final ApiQueryInfo apiQueryInfo;


	@Override
	public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response,
		final Object handler, final Exception ex) {
		final int queryCount = apiQueryInfo.getCount();
		if (queryCount >= QUERY_COUNT_WARNING_STANDARD) {
			log.warn(QUERY_COUNT_WARNING_LOG_FORMAT, QUERY_COUNT_WARNING_STANDARD);
		}
	}
}