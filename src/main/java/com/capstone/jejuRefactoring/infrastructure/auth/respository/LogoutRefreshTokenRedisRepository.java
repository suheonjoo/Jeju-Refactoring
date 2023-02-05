package com.capstone.jejuRefactoring.infrastructure.auth.respository;

import org.springframework.data.repository.CrudRepository;

import com.capstone.jejuRefactoring.domain.auth.LogoutRefreshToken;

public interface LogoutRefreshTokenRedisRepository extends CrudRepository<LogoutRefreshToken, String> {
}
