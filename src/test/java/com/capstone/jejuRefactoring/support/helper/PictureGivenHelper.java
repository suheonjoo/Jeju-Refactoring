package com.capstone.jejuRefactoring.support.helper;

import com.capstone.jejuRefactoring.domain.picture.Picture;
import com.capstone.jejuRefactoring.domain.spot.Spot;

public class PictureGivenHelper {
	public static Picture givenPicture(Spot spot) {
		return Picture.builder()
			.spot(spot)
			.url("http:~~~")
			.build();
	}
}
