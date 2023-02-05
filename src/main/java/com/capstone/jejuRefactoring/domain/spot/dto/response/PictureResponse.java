package com.capstone.jejuRefactoring.domain.spot.dto.response;

import com.capstone.jejuRefactoring.domain.spot.Picture;
import com.capstone.jejuRefactoring.domain.spot.Spot;

import lombok.Getter;

@Getter
public class PictureResponse {

	private Long pictureId;
	private boolean spotMatch;
	private String url;
	private Long spotId;

	private PictureResponse(Long pictureId, boolean spotMatch, String url, Long spotId) {
		this.pictureId = pictureId;
		this.spotMatch = spotMatch;
		this.url = url;
		this.spotId = spotId;
	}

	public static PictureResponse of(final Picture picture, final Long spotId) {
		final Spot spot = picture.getSpot();
		return new PictureResponse(picture.getId(), spot.isSameId(spotId), picture.getUrl(), picture.getSpot().getId());
	}

}
