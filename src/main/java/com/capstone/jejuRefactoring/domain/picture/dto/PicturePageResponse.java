package com.capstone.jejuRefactoring.domain.picture.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Slice;

import com.capstone.jejuRefactoring.domain.picture.Picture;

import lombok.Getter;

@Getter
public class PicturePageResponse {

	private final boolean hasNext;
	private final List<PictureResponse> pictureResponse;

	private PicturePageResponse(final boolean hasNext, final List<PictureResponse> pictureResponse) {
		this.hasNext = hasNext;
		this.pictureResponse = pictureResponse;
	}

	public static PicturePageResponse of(final Slice<Picture> slice, final Long spotId) {
		final List<PictureResponse> pictures = slice.getContent()
			.stream()
			.map(pictureTag -> PictureResponse.of(pictureTag, spotId))
			.collect(Collectors.toList());
		return new PicturePageResponse(slice.hasNext(), pictures);
	}

}
