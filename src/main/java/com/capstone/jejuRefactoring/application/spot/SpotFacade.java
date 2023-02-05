package com.capstone.jejuRefactoring.application.spot;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.capstone.jejuRefactoring.domain.priority.service.PriorityService;
import com.capstone.jejuRefactoring.domain.spot.dto.response.PicturePageResponse;
import com.capstone.jejuRefactoring.domain.spot.dto.response.SpotResponse;
import com.capstone.jejuRefactoring.domain.spot.service.SpotService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SpotFacade {
	private final SpotService spotService;
	private final PriorityService priorityService;

	public PicturePageResponse getPicturesBySpotId(final Long spotId, Pageable pageable) {
		return spotService.getPicturesBySpotId(spotId, pageable);
	}

	public SpotResponse getBySpotId(final Long spotId) {
		SpotResponse spotBySpotId = spotService.getBySpotId(spotId);
		// Todo: 2. SpotResponse 에 score 정보도 넣어주기
		return priorityService.getScoreBySpotId(spotBySpotId, spotId);
	}

}
