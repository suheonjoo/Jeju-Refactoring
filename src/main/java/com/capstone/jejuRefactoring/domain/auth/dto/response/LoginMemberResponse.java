package com.capstone.jejuRefactoring.domain.auth.dto.response;

import com.capstone.jejuRefactoring.domain.auth.Member;

import lombok.Getter;

@Getter
public class LoginMemberResponse {

	private Long id;
	private String email;
	private String name;

	private LoginMemberResponse() {
	}

	private LoginMemberResponse(final Long id, final String email, final String name) {
		this.id = id;
		this.email = email;
		this.name = name;
	}

	public static LoginMemberResponse from(final Member member) {
		return new LoginMemberResponse(member.getId(), member.getEmail(), member.getUsername());
	}
}

