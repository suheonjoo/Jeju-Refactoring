package com.capstone.jejuRefactoring.presentation.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginRequest {

	@NotEmpty
	@Email(message = "이메일 형식이어야 합니다")
	private String email;

	@NotEmpty
	@NotEmpty(message = "공백을 입력할수 없습니다")
	private String password;

}







