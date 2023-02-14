package com.capstone.jejuRefactoring.config;

import static org.springframework.security.config.http.SessionCreationPolicy.*;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.capstone.jejuRefactoring.config.security.filter.JwtAuthenticationFilter;
import com.capstone.jejuRefactoring.config.security.filter.LoginProcessingAuthenticationFilter;
import com.capstone.jejuRefactoring.config.security.handler.LoginFailureHandler;
import com.capstone.jejuRefactoring.config.security.handler.LoginSuccessHandler;
import com.capstone.jejuRefactoring.config.security.handler.RestAccessDeniedHandler;
import com.capstone.jejuRefactoring.config.security.handler.RestAuthenticationEntryPoint;

import lombok.RequiredArgsConstructor;

//Spring Security를 사용하기 위해서는 Spring Security Filter Chain 을 사용한다는 것을 명시해 주기 위해 @EnableWebSecurity 사용
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig {

	private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final LoginSuccessHandler loginSuccessHandler;
	private final LoginFailureHandler loginFailureHandler;
	private final AuthenticationConfiguration authenticationConfiguration;

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {

		return (web) -> web.ignoring()
			.requestMatchers(PathRequest.toStaticResources().atCommonLocations())
			.requestMatchers("/h2-console/**");
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.headers().frameOptions().disable();
		http
			.httpBasic().disable()
			.csrf().disable()
			.sessionManagement().sessionCreationPolicy(STATELESS)

			.and()
			.exceptionHandling()
			.authenticationEntryPoint(restAuthenticationEntryPoint) // 인증 실패
			.accessDeniedHandler(new RestAccessDeniedHandler()) // 인가 실패

			.and()
			.addFilterAt(customLoginProcessingAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
			.addFilterBefore(jwtAuthenticationFilter, LoginProcessingAuthenticationFilter.class);
		http.cors();

		return http.build();
	}

	public LoginProcessingAuthenticationFilter customLoginProcessingAuthenticationFilter() throws Exception {
		LoginProcessingAuthenticationFilter loginProcessingAuthenticationFilter
			= new LoginProcessingAuthenticationFilter(new AntPathRequestMatcher("/api/v1/login", "POST"));
		loginProcessingAuthenticationFilter.setAuthenticationManager(
			authenticationManager(authenticationConfiguration));
		loginProcessingAuthenticationFilter
			.setAuthenticationSuccessHandler(loginSuccessHandler);
		loginProcessingAuthenticationFilter
			.setAuthenticationFailureHandler(loginFailureHandler);
		return loginProcessingAuthenticationFilter;

	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws
		Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

}
