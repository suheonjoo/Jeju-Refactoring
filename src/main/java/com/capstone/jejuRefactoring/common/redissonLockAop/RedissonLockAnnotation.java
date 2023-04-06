package com.capstone.jejuRefactoring.common.redissonLockAop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)//이 MethodAop라는 애노테이션은 메서드 타입에 적용하는 것이라고 지정함
@Retention(RetentionPolicy.RUNTIME)//source로 하면 아래 애노테이션이 사라짐
public @interface RedissonLockAnnotation {
}
