package com.capstone.jejuRefactoring.presentation.spot;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.jejuRefactoring.application.spot.SpotFacade;
import com.capstone.jejuRefactoring.common.exception.CommonResponse;
import com.capstone.jejuRefactoring.common.metaDataBuilder.DefaultMetaDataBuilder;
import com.capstone.jejuRefactoring.common.metaDataBuilder.MetaDataDirector;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/spots")
@RequiredArgsConstructor
public class SpotController {

	private final SpotFacade spotFacade;

	@GetMapping("/{spotId}")
	public ResponseEntity<CommonResponse> showSpot(@PathVariable final Long spotId) {
		return ResponseEntity.ok()
			.body(CommonResponse.success(spotFacade.getBySpotId(spotId)));
	}

	@GetMapping("/{spotName}")
	public ResponseEntity<CommonResponse> searchSpotBySpotName(@PathVariable final String spotName,
		@RequestParam(value = "lastSpotId", required = false) Long lastSpotId, Pageable pageable) {
		return ResponseEntity.ok()
			.body(CommonResponse.success(spotFacade.getSpotBySpotName(spotName, lastSpotId, pageable)));
	}

	@GetMapping("/metaData")
	public ResponseEntity<CommonResponse> getMetaDataOp() {

		MetaDataDirector metaDataDirector = new MetaDataDirector(new DefaultMetaDataBuilder());
		return ResponseEntity.ok()
			.body(CommonResponse.success(metaDataDirector));
	}

}
