package com.capstone.jejuRefactoring.config.security.filter;

import static com.capstone.jejuRefactoring.config.security.provider.JwtTokenProvider.*;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.capstone.jejuRefactoring.common.exception.security.TokenAuthenticationFilterException;
import com.capstone.jejuRefactoring.config.security.provider.JwtTokenProvider;
import com.capstone.jejuRefactoring.domain.auth.repository.TokenRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtTokenProvider jwtTokenProvider;
	private final TokenRepository tokenRepository;
	private final UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		try {
			String jwt = getJwtFromRequest(request);
			if (isValidToken(jwt)) {
				UserDetails userDetails = userDetailsService.loadUserByUsername(jwtTokenProvider.getUserPk(jwt));
				Authentication authentication = (Authentication)new UsernamePasswordAuthenticationToken(
					userDetails, null, userDetails.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		} catch (Exception e) {
			throw new TokenAuthenticationFilterException(e);
		}
		filterChain.doFilter(request, response);
	}

	private boolean isValidToken(String jwt) {
		return StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)
			&& !tokenRepository.existsLogoutAccessTokenById(jwt)
			&& !tokenRepository.existsLogoutRefreshTokenById(jwt);
	}

	private String getJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader(ACCESS_TOKEN);
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(jwtTokenProvider.TOKEN_TYPE)) {
			return bearerToken.substring(7);
		}
		return null;
	}

}

