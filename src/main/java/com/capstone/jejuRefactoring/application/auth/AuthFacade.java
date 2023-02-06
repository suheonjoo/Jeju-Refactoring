package com.capstone.jejuRefactoring.application.auth;

import java.util.List;

import org.springframework.stereotype.Service;

import com.capstone.jejuRefactoring.domain.auth.Member;
import com.capstone.jejuRefactoring.domain.auth.dto.AuthResponseDto;
import com.capstone.jejuRefactoring.domain.auth.service.AuthCommandUseCase;
import com.capstone.jejuRefactoring.domain.priority.service.PriorityService;
import com.capstone.jejuRefactoring.domain.spot.service.SpotService;
import com.capstone.jejuRefactoring.presentation.auth.dto.request.JoinRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthFacade {

	private final AuthCommandUseCase authCommandUseCase;
	private final PriorityService priorityService;
	private final SpotService spotService;

	public void logout(String accessToken, String refreshToken) {
		authCommandUseCase.logout(accessToken, refreshToken);
	}

	public void join(JoinRequest joinRequest) {
		Long memberId = authCommandUseCase.join(joinRequest);
		List<Long> spotIds = spotService.getAllSpotIds();
		priorityService.createMemberSpotTages(memberId, spotIds);
	}

	public void deleteMember(Member member, String accessToken, String refreshToken) {
		authCommandUseCase.deleteMember(member, accessToken, refreshToken);
	}

	public AuthResponseDto reissue(String refreshToken) {
		return authCommandUseCase.reissue(refreshToken);
	}
}

