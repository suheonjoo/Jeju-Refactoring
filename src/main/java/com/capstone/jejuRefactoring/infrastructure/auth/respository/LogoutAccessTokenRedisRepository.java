package com.capstone.jejuRefactoring.infrastructure.auth.respository;

import org.springframework.data.repository.CrudRepository;

import com.capstone.jejuRefactoring.domain.auth.LogoutAccessToken;

public interface LogoutAccessTokenRedisRepository extends CrudRepository<LogoutAccessToken, String> {

}
