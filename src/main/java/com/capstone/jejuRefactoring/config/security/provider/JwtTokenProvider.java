package com.capstone.jejuRefactoring.config.security.provider;

import java.time.Duration;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.Cookie;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtTokenProvider {

	public static final String TOKEN_TYPE = "Bearer ";
	public static final String REFRESH_TOKEN = "refreshToken";
	public static final String ACCESS_TOKEN = "accessToken";

	private final String secretKey;
	private final long accessTokenExpirationTimeInMilliSeconds;
	private final long refreshTokenExpirationTimeInMilliSeconds;
	private final long reissueRefreshTokenTimeInMilliSeconds;

	public JwtTokenProvider(
		@Value("${jwt.secret-key}") String secretKey,
		@Value("${jwt.access-expiration-time}") long accessTokenExpirationTimeInMilliSeconds,
		@Value("${jwt.refresh-expiration-time}") long refreshTokenExpirationTimeInMilliSeconds,
		@Value("${jwt.reissue-refresh-time}") long reissueRefreshTokenTimeInMilliSeconds
	) {
		this.secretKey = secretKey;
		this.accessTokenExpirationTimeInMilliSeconds = accessTokenExpirationTimeInMilliSeconds;
		this.refreshTokenExpirationTimeInMilliSeconds = refreshTokenExpirationTimeInMilliSeconds;
		this.reissueRefreshTokenTimeInMilliSeconds = reissueRefreshTokenTimeInMilliSeconds;
	}

	public String createAccessToken(Authentication authentication) {
		return createToken(authentication, accessTokenExpirationTimeInMilliSeconds);
	}

	public String createRefreshToken(Authentication authentication) {
		return createToken(authentication, refreshTokenExpirationTimeInMilliSeconds);
	}

	private String createToken(Authentication authentication, long accessTokenExpirationTimeInMilliSeconds) {
		String principal = (String)authentication.getPrincipal();

		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + accessTokenExpirationTimeInMilliSeconds);

		return Jwts.builder()
			.setSubject(principal)
			.setIssuedAt(now)
			.setExpiration(expiryDate)
			.signWith(SignatureAlgorithm.HS512, secretKey)
			.compact();
	}

	public ResponseCookie createCookie(final String refreshToken) {
		return createTokenCookieBuilder(refreshToken)
			.maxAge(Duration.ofMillis(JwtExpirationEnums.REFRESH_TOKEN_EXPIRATION_TIME.getValue()))
			.build();
	}

	private ResponseCookie.ResponseCookieBuilder createTokenCookieBuilder(final String value) {
		return ResponseCookie.from(REFRESH_TOKEN, value)
			.httpOnly(true)
			.secure(true)
			.path("/")
			.sameSite(Cookie.SameSite.NONE.attributeValue());
	}

	public String removeType(String token) {
		return token.substring(TOKEN_TYPE.length());
	}

	public boolean isMoreThanReissueTime(String token) {
		return getRemainingMilliSecondsFromToken(token) >= reissueRefreshTokenTimeInMilliSeconds;
	}

	// public Authentication getAuthentication(String token) {
	// 	UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
	// 	return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	// }

	public String getUserPk(String token) {
		return Jwts.parser()
			.setSigningKey(secretKey)
			.parseClaimsJws(token)
			.getBody()
			.getSubject();
	}

	public boolean validateToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken);
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			return false;
		}
	}

	private Claims getClaims(String token) {
		return Jwts.parser()
			.setSigningKey(secretKey)
			.parseClaimsJws(token)
			.getBody();
	}

	public long getRemainingMilliSecondsFromToken(String token) {
		Date expiration = getClaims(token).getExpiration();
		return expiration.getTime() - (new Date()).getTime();
	}

}






