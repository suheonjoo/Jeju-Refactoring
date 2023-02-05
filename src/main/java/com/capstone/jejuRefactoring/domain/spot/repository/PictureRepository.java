package com.capstone.jejuRefactoring.domain.spot.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import com.capstone.jejuRefactoring.domain.spot.Picture;
import com.capstone.jejuRefactoring.infrastructure.spot.PictureUrlDto;

public interface PictureRepository {
	List<Picture> findBySpotIds(List<Long> spotIds);

	Slice<Picture> findPageBySpotId(Long spotId, Pageable pageable);

	List<PictureUrlDto> findNPictureForSpotIds(List<Long> spoIds, Integer limit);
}
