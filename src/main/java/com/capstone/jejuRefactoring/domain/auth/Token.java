package com.capstone.jejuRefactoring.domain.auth;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.TimeToLive;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Token {

	@Id
	private String id;

	@TimeToLive(unit = TimeUnit.MILLISECONDS)
	private long expiration;

}