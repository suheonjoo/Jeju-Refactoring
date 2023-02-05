package com.capstone.jejuRefactoring.domain.auth.dto.response;

import com.capstone.jejuRefactoring.domain.auth.Member;
import com.capstone.jejuRefactoring.domain.auth.dto.LoginResult;

import lombok.Getter;

@Getter
public class LoginResponse {

	private String token;
	private LoginMemberResponse member;

	private LoginResponse() {
	}

	public LoginResponse(final String token,
		final LoginMemberResponse loginMemberResponse) {
		this.token = token;
		this.member = loginMemberResponse;
	}

	public static LoginResponse from(final LoginResult loginResult) {
		final Member member = loginResult.getMember();
		final LoginMemberResponse loginMemberResponse = LoginMemberResponse.from(member);
		return new LoginResponse(loginResult.getAccessToken(), loginMemberResponse);
	}
}