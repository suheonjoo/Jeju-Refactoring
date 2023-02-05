package com.capstone.jejuRefactoring.domain.spot.dto.response;

import com.capstone.jejuRefactoring.domain.spot.Location;
import com.capstone.jejuRefactoring.domain.spot.Spot;
import com.capstone.jejuRefactoring.infrastructure.spot.PictureUrlDto;

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
public class SpotForRouteDto {

	private Long id;

	private String name;

	private String address;

	@Lob
	private String description;

	private Location location;

	@Lob
	private PictureUrlDto pictureUrlDto;

	public static SpotForRouteDto of(Spot spot, PictureUrlDto pictureUrlDto) {
		return SpotForRouteDto.builder()
			.id(spot.getId())
			.name(spot.getName())
			.description(spot.getDescription())
			.address(spot.getAddress())
			.location(spot.getLocation())
			.pictureUrlDto(pictureUrlDto)
			.build();
	}

	public static SpotForRouteDto from(Spot spot) {
		return SpotForRouteDto.builder()
			.id(spot.getId())
			.name(spot.getName())
			.description(spot.getDescription())
			.address(spot.getAddress())
			.location(spot.getLocation())
			.build();
	}

}
