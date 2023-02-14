package com.capstone.jejuRefactoring.config;

import static org.junit.jupiter.api.Assertions.*;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;

@TestConfiguration
@ComponentScan(basePackages = "com.capstone.jejuRefactoring.config.security")
class SecurityTestConfig {

}