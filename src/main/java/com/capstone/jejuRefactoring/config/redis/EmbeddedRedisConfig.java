package com.capstone.jejuRefactoring.config.redis;// package com.springjwt.springsecurityjwt.config.redis;//package capstone.jejuTourrecommend.config.redis;
//
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.data.redis.connection.RedisConnectionFactory;
// import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
// import org.springframework.util.ObjectUtils;
//
// import jakarta.annotation.PostConstruct;
// import jakarta.annotation.PreDestroy;
// import redis.embedded.RedisServer;
//
// import java.io.BufferedReader;
// import java.io.IOException;
// import java.io.InputStreamReader;
//
// @Configuration
// public class EmbeddedRedisConfig {
//
// 	@Value("${spring.redis.port}")
// 	private int port;
//
// 	@Value("${spring.redis.host}")
// 	private String host;
//
// 	private RedisServer redisServer;
//
// 	@Bean
// 	public RedisConnectionFactory redisConnectionFactory() {
// 		LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(host, port);
// 		return lettuceConnectionFactory;
// 	}
//
// 	@PostConstruct
// 	public void redisServer() throws IOException {
// 		int port = isRedisRunning() ? findAvailablePort() : this.port;
// 		redisServer = new RedisServer(port);
// 		redisServer.start();
// 	}
//
// 	/**
// 	 * Embedded Redis가 현재 실행중인지 확인
// 	 */
// 	private boolean isRedisRunning() throws IOException {
// 		return isRunning(executeGrepProcessCommand(port));
// 	}
//
// 	/**
// 	 * 해당 Process가 현재 실행중인지 확인
// 	 */
// 	private boolean isRunning(Process process) {
// 		String line;
// 		StringBuilder pidInfo = new StringBuilder();
//
// 		try (BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
//
// 			while ((line = input.readLine()) != null) {
// 				pidInfo.append(line);
// 			}
// 		} catch (Exception e) {
// 		}
//
// 		return !ObjectUtils.isEmpty(pidInfo.toString());
// 	}
//
// 	/**
// 	 * 현재 PC/서버에서 사용가능한 포트 조회
// 	 */
// 	public int findAvailablePort() throws IOException {
//
// 		for (int port = 10000; port <= 65535; port++) {
// 			Process process = executeGrepProcessCommand(port);
// 			if (!isRunning(process)) {
// 				return port;
// 			}
// 		}
// 		throw new IllegalArgumentException("Not Found Available port: 10000 ~ 65535");
// 	}
//
// 	/**
// 	 * 해당 port를 사용중인 프로세스 확인하는 sh 실행
// 	 */
// 	private Process executeGrepProcessCommand(int port) throws IOException {
// 		String command = String.format("netstat -nat | grep LISTEN|grep %d", port);
// 		String[] shell = {"/bin/sh", "-c", command};
// 		return Runtime.getRuntime().exec(shell);
// 	}
//
// 	@PreDestroy
// 	public void stopRedis() {
// 		if (redisServer != null && redisServer.isActive()) {
// 			redisServer.stop();
// 		}
// 	}
// }
