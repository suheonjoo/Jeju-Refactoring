package com.capstone.jejuRefactoring.domain.spot.repository;

import java.util.List;

import com.capstone.jejuRefactoring.domain.spot.PictureTag;
import com.capstone.jejuRefactoring.infrastructure.spot.PictureTagUrlDto;

public interface PictureTagRepository {
	List<PictureTag> findBySpotIds(List<Long> spotIds);

	List<PictureTagUrlDto> findNPictureTagForSpotIds(List<Long> spoIds, Integer limit);
}
