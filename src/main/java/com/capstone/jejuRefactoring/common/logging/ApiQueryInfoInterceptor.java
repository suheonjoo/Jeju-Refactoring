package com.capstone.jejuRefactoring.common.logging;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApiQueryInfoInterceptor implements HandlerInterceptor {

    private static final String QUERY_INFO_LOG_FORMAT = "STATUS_CODE: {}, METHOD: {}, URL: {}, QUERY_COUNT: {}, RUNNING_TIME: {} (ms)";

    private final ApiQueryInfo apiQueryInfo;

    @Override
    public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response,
                                final Object handler, final Exception ex) {
        String requestURI = request.getRequestURI();
        String method = request.getMethod();
        int status = response.getStatus();
        int count = apiQueryInfo.getCount();
        long runningTime = apiQueryInfo.getRunningTime();
        log.info(QUERY_INFO_LOG_FORMAT, status, method, requestURI, count, runningTime);
    }
}
