package com.capstone.jejuRefactoring.domain.spot.dto.response;

import java.util.List;

import com.capstone.jejuRefactoring.domain.spot.Location;
import com.capstone.jejuRefactoring.domain.spot.Spot;

import jakarta.persistence.Lob;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SpotWithPicturesDto {

	private Long id;

	private String name;

	private String address;

	@Lob
	private String description;

	private Location location;

	private List<PictureResponse> pictureResponses;

	public static SpotWithPicturesDto of(Spot spot, List<PictureResponse> pictureResponses) {
		return SpotWithPicturesDto.builder()
			.id(spot.getId())
			.name(spot.getName())
			.description(spot.getDescription())
			.address(spot.getAddress())
			.location(spot.getLocation())
			.pictureResponses(pictureResponses)
			.build();
	}



}
