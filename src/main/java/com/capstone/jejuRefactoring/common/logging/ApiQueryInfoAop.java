package com.capstone.jejuRefactoring.common.logging;

import java.lang.reflect.Proxy;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
@RequiredArgsConstructor
public class ApiQueryInfoAop {

    private final ApiQueryInfo apiQueryInfo;

    @Around("execution(* javax.sql.DataSource.getConnection())")
    public Object getConnection(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object connection = proceedingJoinPoint.proceed();
        return Proxy.newProxyInstance(
                connection.getClass().getClassLoader(),
                connection.getClass().getInterfaces(), //프록시에 핸들러 로직도 같이 넣어준다
                //ConnectionProxyHandler 에 실제 Connection 객체와 부가로직 기능을 하는 apiQueryInfo 를 같이 넣는다
                new ConnectionProxyHandler(connection, apiQueryInfo)
        );
    }
}
