package com.capstone.jejuRefactoring.common.redissonLockAop;

import java.util.concurrent.TimeUnit;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import com.capstone.jejuRefactoring.common.exception.priority.NotLockException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@RequiredArgsConstructor
@Component
public class RedissonLockAop {

	private final RedissonClient redissonClient;

	@Around("@annotation(com.capstone.jejuRefactoring.common.redissonLockAop.RedissonLockAnnotation)")
	public Object getRedissonLockAnnotation(ProceedingJoinPoint joinPoint) throws Throwable {
		log.info("[@annotation] {}", joinPoint.getSignature());
		Object proceed;
		Object[] args = joinPoint.getArgs();
		String lockName = args[0].toString();
		// log.info("lockName = {}",lockName);
		RLock lock = redissonClient.getLock(lockName);
		try {
			validatedLock(lock.tryLock(15, 1, TimeUnit.SECONDS));
			proceed = joinPoint.proceed();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			lock.unlock();
		}
		return proceed;
	}

	private void validatedLock(boolean available) {
		if (!available) {
			throw new NotLockException();
		}
	}

}
