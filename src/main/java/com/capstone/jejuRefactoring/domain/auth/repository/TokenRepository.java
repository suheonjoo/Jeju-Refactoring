package com.capstone.jejuRefactoring.domain.auth.repository;

import com.capstone.jejuRefactoring.domain.auth.LogoutAccessToken;
import com.capstone.jejuRefactoring.domain.auth.LogoutRefreshToken;
import com.capstone.jejuRefactoring.domain.auth.RefreshToken;

public interface TokenRepository {

	void saveLogoutAccessToken(LogoutAccessToken logoutAccessToken);

	void saveLogoutRefreshToken(LogoutRefreshToken logoutRefreshToken);

	void saveRefreshToken(RefreshToken refreshToken);

	boolean existsLogoutAccessTokenById(String token);

	boolean existsLogoutRefreshTokenById(String token);

	boolean existsRefreshTokenById(String token);

	void deleteRefreshTokenById(String token);

}
