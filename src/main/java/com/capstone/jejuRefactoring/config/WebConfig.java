package com.capstone.jejuRefactoring.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.exposedHeaders("ACCESS-TOKEN")
			.allowCredentials(true)
			.allowedOrigins("http://localhost:3000", "http://3.34.29.242:8080")
			.allowedMethods("*")
			.maxAge(3600);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	private final List<HandlerInterceptor> interceptors;

	public WebConfig(final List<HandlerInterceptor> interceptors) {
		this.interceptors = interceptors;
	}

	@Override
	public void addInterceptors(final InterceptorRegistry registry) {
		interceptors.forEach(registry::addInterceptor);
	}

}

