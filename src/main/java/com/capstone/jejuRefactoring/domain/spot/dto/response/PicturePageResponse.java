package com.capstone.jejuRefactoring.domain.spot.dto.response;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Slice;

import com.capstone.jejuRefactoring.domain.spot.Picture;

import lombok.Getter;

@Getter
public class PicturePageResponse {

	private final boolean hasNext;
	private final List<PictureResponse> prictures;

	private PicturePageResponse(final boolean hasNext, final List<PictureResponse> pictureResponses) {
		this.hasNext = hasNext;
		this.prictures = pictureResponses;
	}

	public static PicturePageResponse of(final Slice<Picture> slice, final Long spotId) {
		final List<PictureResponse> pictures = slice.getContent()
			.stream()
			.map(picture -> PictureResponse.of(picture, spotId))
			.collect(Collectors.toList());
		return new PicturePageResponse(slice.hasNext(), pictures);
	}

}
