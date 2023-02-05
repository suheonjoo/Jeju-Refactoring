package com.capstone.jejuRefactoring.domain.auth;

public enum Role {
	USER("ROLE_USER"), ADMIN("ROLE_ADMIN");

	private String roleName;

	Role(String role) {
		this.roleName = role;
	}

	public String getRoleName() {
		return roleName;
	}
}

