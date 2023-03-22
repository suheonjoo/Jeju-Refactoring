package com.capstone.jejuRefactoring.domain.picture.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capstone.jejuRefactoring.domain.picture.Picture;
import com.capstone.jejuRefactoring.domain.picture.dto.PicturePageResponse;
import com.capstone.jejuRefactoring.domain.picture.repsoitory.PictureRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PictureService {

	private final PictureRepository pictureRepository;

	public PicturePageResponse getPicturesBySpotId(final Long spotId, Long lastPictureId, Pageable pageable) {
		final Slice<Picture> page = pictureRepository.findPageOpBySpotId(spotId,lastPictureId, pageable);
		return PicturePageResponse.of(page, spotId);
	}
}
