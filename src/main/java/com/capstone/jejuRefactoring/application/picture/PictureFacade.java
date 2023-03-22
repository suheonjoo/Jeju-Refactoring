package com.capstone.jejuRefactoring.application.picture;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capstone.jejuRefactoring.domain.picture.dto.PicturePageResponse;
import com.capstone.jejuRefactoring.domain.picture.service.PictureService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PictureFacade {

	private final PictureService pictureService;

	public PicturePageResponse getPicturesBySpotId(final Long spotId,Long lastPictureId, Pageable pageable) {
		return pictureService.getPicturesBySpotId(spotId,lastPictureId, pageable);
	}
}
