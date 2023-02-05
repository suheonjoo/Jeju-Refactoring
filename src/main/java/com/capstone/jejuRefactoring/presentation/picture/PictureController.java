package com.capstone.jejuRefactoring.presentation.picture;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.jejuRefactoring.application.picture.PictureFacade;
import com.capstone.jejuRefactoring.common.exception.CommonResponse;
import com.capstone.jejuRefactoring.domain.picture.dto.PicturePageResponse;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/v1/spots")
@RequiredArgsConstructor
public class PictureController {

	private final PictureFacade pictureFacade;

	@GetMapping("/{spotId}/pictures")
	public ResponseEntity<CommonResponse> showPictures(@PathVariable final Long spotId, Pageable pageable) {
		PicturePageResponse picturesBySpotId = pictureFacade.getPicturesBySpotId(spotId, pageable);
		return ResponseEntity.ok()
			.body(CommonResponse.success(picturesBySpotId));
	}

}