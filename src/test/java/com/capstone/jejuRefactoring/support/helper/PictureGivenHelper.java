package com.capstone.jejuRefactoring.support.helper;

import com.capstone.jejuRefactoring.domain.picture.Picture;
import com.capstone.jejuRefactoring.domain.spot.Spot;

public class PictureGivenHelper {
	public static Picture givenPicture(Long spotId) {
		return Picture.builder()
			.spot(Spot.builder().id(spotId).build())
			.url("http:~~~")
			.build();
	}
}
