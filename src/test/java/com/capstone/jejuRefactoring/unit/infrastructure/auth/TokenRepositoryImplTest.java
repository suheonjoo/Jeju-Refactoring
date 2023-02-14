package com.capstone.jejuRefactoring.unit.infrastructure.auth;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.capstone.jejuRefactoring.domain.auth.LogoutAccessToken;
import com.capstone.jejuRefactoring.domain.auth.LogoutRefreshToken;
import com.capstone.jejuRefactoring.domain.auth.repository.TokenRepository;
import com.capstone.jejuRefactoring.infrastructure.auth.respository.LogoutAccessTokenRedisRepository;
import com.capstone.jejuRefactoring.infrastructure.auth.respository.LogoutRefreshTokenRedisRepository;
import com.capstone.jejuRefactoring.infrastructure.auth.respository.RefreshTokenRedisRepository;
import com.capstone.jejuRefactoring.infrastructure.auth.respository.TokenRepositoryImpl;
import com.capstone.jejuRefactoring.support.RedisRepositoryTest;

public class TokenRepositoryImplTest extends RedisRepositoryTest {

    @Autowired
    LogoutAccessTokenRedisRepository logoutAccessTokenRedisRepository;
    @Autowired
    LogoutRefreshTokenRedisRepository logoutRefreshTokenRedisRepository;
    @Autowired
    RefreshTokenRedisRepository refreshTokenRedisRepository;
    TokenRepository tokenRepository;

    @BeforeEach
    void setup(){
        tokenRepository = new TokenRepositoryImpl(logoutAccessTokenRedisRepository,logoutRefreshTokenRedisRepository,refreshTokenRedisRepository);
    }

    @BeforeEach
    void teardown(){
        logoutAccessTokenRedisRepository.deleteAll();
        logoutRefreshTokenRedisRepository.deleteAll();
    }

    @Test
    void LogoutAccessToken_저장() throws Exception{

        //given
        String accessToken = "accessToken";
        long expiration = 3600000L;

        //when
        tokenRepository.saveLogoutAccessToken(LogoutAccessToken.of(accessToken, expiration));

        //then
        assertThat(tokenRepository.existsLogoutAccessTokenById(accessToken)).isTrue();
    }

    @Test
    void LogoutRefreshToken_저장() throws Exception{
        //given
        String refreshToken = "refreshToken";
        long expiration = 3600000L;

        //when
        tokenRepository.saveLogoutRefreshToken(LogoutRefreshToken.of(refreshToken, expiration));


        //then
        assertThat(tokenRepository.existsLogoutRefreshTokenById(refreshToken)).isTrue();
    }

    @Test
    void accessToken이_저장되어_있는지_확인() throws Exception{

        //given
        String accessToken = "accessToken";
        long expiration = 3600000L;
        tokenRepository.saveLogoutAccessToken(LogoutAccessToken.of(accessToken, expiration));

        //when then
        assertThat(tokenRepository.existsLogoutAccessTokenById(accessToken)).isTrue();
    }

    @Test
    void refreshToken이_저장되어_있는지_확인() throws Exception{

        //given
        String refreshToken = "refreshToken";
        long expiration = 3600000L;
        tokenRepository.saveLogoutRefreshToken(LogoutRefreshToken.of(refreshToken, expiration));

        //when then
        assertThat(tokenRepository.existsLogoutRefreshTokenById(refreshToken)).isTrue();
    }
}
