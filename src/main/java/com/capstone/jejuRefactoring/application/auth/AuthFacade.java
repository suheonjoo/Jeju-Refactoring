package com.capstone.jejuRefactoring.application.auth;

import java.util.List;

import org.springframework.stereotype.Service;

import com.capstone.jejuRefactoring.domain.auth.Member;
import com.capstone.jejuRefactoring.domain.auth.dto.AuthResponseDto;
import com.capstone.jejuRefactoring.domain.auth.service.AuthCommandUseCase;
import com.capstone.jejuRefactoring.domain.preference.service.PreferenceService;
import com.capstone.jejuRefactoring.domain.spot.service.SpotService;
import com.capstone.jejuRefactoring.presentation.auth.dto.request.JoinRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthFacade {
	private final AuthCommandUseCase authCommandUseCase;
	private final PreferenceService preferenceService;
	private final SpotService spotService;

	public void logout(String accessToken, String refreshToken) {
		authCommandUseCase.logout(accessToken, refreshToken);
	}

	public void join(JoinRequest joinRequest) {
		Long memberId = authCommandUseCase.join(joinRequest);
		List<Long> spotIds = spotService.getAllSpotIds();
		log.info("spotIds = {}",spotIds);
		preferenceService.createMemberSpotTags(memberId, spotIds);
	}

	public void deleteMember(Member member, String accessToken, String refreshToken) {
		authCommandUseCase.deleteMember(member, accessToken, refreshToken);
	}

	public AuthResponseDto reissue(String refreshToken) {
		return authCommandUseCase.reissue(refreshToken);
	}
}

