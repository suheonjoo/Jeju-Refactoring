package com.capstone.jejuRefactoring.common.logging;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ConnectionProxyHandler implements InvocationHandler {

    private final Object connection;
    private final ApiQueryInfo apiQueryInfo;

    public ConnectionProxyHandler(final Object connection, final ApiQueryInfo apiQueryInfo) {
        this.connection = connection;
        this.apiQueryInfo = apiQueryInfo;
    }

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        Object invokeResult = method.invoke(connection, args);
        if (method.getName().equals("prepareStatement")) {
            return Proxy.newProxyInstance(
                    invokeResult.getClass().getClassLoader(),
                    invokeResult.getClass().getInterfaces(),
                    new PreparedStatementProxyHandler(invokeResult, apiQueryInfo)
            );
        }
        return invokeResult;
    }
}
