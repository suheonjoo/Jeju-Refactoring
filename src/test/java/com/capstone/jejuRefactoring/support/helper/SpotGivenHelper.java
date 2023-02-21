package com.capstone.jejuRefactoring.support.helper;

import com.capstone.jejuRefactoring.domain.spot.Location;
import com.capstone.jejuRefactoring.domain.spot.PictureTag;
import com.capstone.jejuRefactoring.domain.spot.Spot;

public class SpotGivenHelper {

	public static Spot givenSpot() {
		return Spot.builder()
			.name("한라산")
			.address("주소1")
			.location(Location.Aewol_eup)
			.description("설명1")
			.build();
	}

	public static Spot givenSpotWithName(String name) {
		return Spot.builder()
			.name(name)
			.address("주소1")
			.location(Location.Aewol_eup)
			.description("설명1")
			.build();
	}

	public static Spot givenSpotWithLocation(Location location) {
		return Spot.builder()
			.address("주소1")
			.location(location)
			.description("설명1")
			.build();
	}

	public static PictureTag givenPictureTag(Spot spot) {
		return PictureTag.builder()
			.url("pictureTagURL1")
			.spot(spot)
			.build();
	}

}
