package com.capstone.jejuRefactoring.application.spot;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capstone.jejuRefactoring.domain.priority.service.PriorityService;
import com.capstone.jejuRefactoring.domain.spot.dto.response.SpotPageResponse;
import com.capstone.jejuRefactoring.domain.spot.dto.response.SpotPageWithPictureTagsResponse;
import com.capstone.jejuRefactoring.domain.spot.dto.response.SpotResponse;
import com.capstone.jejuRefactoring.domain.spot.service.SpotService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SpotFacade {
	private final SpotService spotService;
	private final PriorityService priorityService;



	public SpotResponse getBySpotId(final Long spotId) {
		SpotResponse spotBySpotId = spotService.getBySpotId(spotId);
		// Todo: 2. SpotResponse 에 score 정보도 넣어주기
		return priorityService.getScoreBySpotId(spotBySpotId, spotId);
	}

	public SpotPageWithPictureTagsResponse getSpotBySpotName(String spotName, Long lastSpotId, Pageable pageable) {
		return spotService.getSpotsBySpotName(spotName, lastSpotId, pageable);
	}

}
