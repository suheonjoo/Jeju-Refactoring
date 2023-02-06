package com.capstone.jejuRefactoring.domain.spot.dto.response;

import com.capstone.jejuRefactoring.domain.spot.PictureTag;

import jakarta.persistence.Lob;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PictureTagDto {

	private Long spotId;
	@Lob
	private String url;

	public static PictureTagDto from(PictureTag pictureTag) {
		return PictureTagDto.builder()
			.spotId(pictureTag.getSpot().getId())
			.url(pictureTag.getUrl())
			.build();
	}

}
