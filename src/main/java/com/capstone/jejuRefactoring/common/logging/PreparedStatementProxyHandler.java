package com.capstone.jejuRefactoring.common.logging;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Objects;
import org.springframework.web.context.request.RequestContextHolder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PreparedStatementProxyHandler implements InvocationHandler {

    private final Object preparedStatement; // 실제 PreparedStatement 객체
    private final ApiQueryInfo apiQueryInfo;

    public PreparedStatementProxyHandler(final Object preparedStatement, final ApiQueryInfo apiQueryInfo) {
        this.preparedStatement = preparedStatement;
        this.apiQueryInfo = apiQueryInfo;
    }

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        if (isExecuteQuery(method) && isInRequestScope()) {
            apiQueryInfo.increaseCount();
            long beforeTime = System.currentTimeMillis();
            Object invoke = method.invoke(preparedStatement, args);
            long afterTime = System.currentTimeMillis();
            long secDiffTime = (afterTime - beforeTime);
            log.info("쿼리 실행시간 (m) : {}", secDiffTime);
            apiQueryInfo.increaseRunningTime(secDiffTime);

            return invoke;
        }
        return method.invoke(preparedStatement, args); //여기서는 굳이 프록시를 반환해줄 필요 없다
    }

    private boolean isExecuteQuery(final Method method) {
        String methodName = method.getName();
        return methodName.equals("executeQuery") || methodName.equals("execute") || methodName.equals("executeUpdate");
    }

    private boolean isInRequestScope() {
        return Objects.nonNull(RequestContextHolder.getRequestAttributes());
    }
}
