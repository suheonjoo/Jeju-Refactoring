package com.capstone.jejuRefactoring.common.exception;

public enum ErrorCode {

	BINDING_EXCEPTION("FORM-400", "적절하지 않은 요청 값입니다."),
	MISSING_REQUEST_VALUE("PARAM-400", "요청 필수 파라미터가 들어오지 않았습니다."),
	NO_AUTHORIZATION_TOKEN("TOKEN-401", "헤더에 Authorization 토큰이 없습니다."),
	NOT_SUPPORT_METHOD("METHOD-405", "지원하지 않는 HTTP Method입니다."),

	INTERNAL_SERVER_ERROR_CODE("SERVER-405", "서버측 오류가 발생했습니다.");

	private final String code;
	private final String message;

	ErrorCode(String code, String message) {
		this.message = message;
		this.code = code;
	}

	public String getMessage() {
		return this.message;
	}

	public String getCode() {
		return code;
	}
}

