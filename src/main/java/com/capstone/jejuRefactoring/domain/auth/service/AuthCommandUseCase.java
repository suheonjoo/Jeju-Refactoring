package com.capstone.jejuRefactoring.domain.auth.service;

import com.capstone.jejuRefactoring.domain.auth.Member;
import com.capstone.jejuRefactoring.domain.auth.dto.AuthResponseDto;
import com.capstone.jejuRefactoring.presentation.auth.dto.request.JoinRequest;

public interface AuthCommandUseCase {

	void logout(String accessToken, String refreshToken);

	Long join(JoinRequest joinRequest);

	void deleteMember(Member member, String accessToken, String refreshToken);

	AuthResponseDto reissue(String refreshToken);

}
