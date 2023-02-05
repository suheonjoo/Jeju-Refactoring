package com.capstone.jejuRefactoring.domain.spot.dto.response;

import com.capstone.jejuRefactoring.domain.spot.PictureTag;
import com.capstone.jejuRefactoring.domain.spot.Spot;

import lombok.Getter;

@Getter
public class PictureTagResponse {

	private Long pictureTagId;
	private boolean spotMatch;
	private String url;
	private Long spotId;

	private PictureTagResponse(Long pictureTagId, boolean spotMatch, String url, Long spotId) {
		this.pictureTagId = pictureTagId;
		this.spotMatch = spotMatch;
		this.url = url;
		this.spotId = spotId;
	}

	public static PictureTagResponse of(final PictureTag pictureTag, final Long spotId) {
		final Spot spot = pictureTag.getSpot();
		return new PictureTagResponse(pictureTag.getId(), spot.isSameId(spotId), pictureTag.getUrl(), pictureTag.getSpot().getId());
	}

}
