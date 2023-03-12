package com.capstone.jejuRefactoring.domain.spot.dto.response;

import java.util.List;
import java.util.Optional;

import com.capstone.jejuRefactoring.domain.spot.PictureTag;
import com.capstone.jejuRefactoring.domain.spot.Spot;

import jakarta.persistence.Lob;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PictureTagDto {

	private Long spotId;
	@Lob
	private String url;

	public static PictureTagDto of(List<PictureTag> pictureTags, Long spotId) {
		return PictureTagDto.builder()
			.spotId(spotId)
			.url(isPictureTagExist(pictureTags))
			.build();
	}

	private static String isPictureTagExist(List<PictureTag> pictureTags) {
		return pictureTags.size() == 0 ? null : pictureTags.get(0).getUrl();
	}

}
