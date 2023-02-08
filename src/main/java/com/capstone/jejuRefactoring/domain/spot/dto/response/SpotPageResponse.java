package com.capstone.jejuRefactoring.domain.spot.dto.response;

import com.capstone.jejuRefactoring.domain.spot.Location;

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
public class SpotPageResponse {

	private Long id;

	private String name;

	private String address;

	@Lob
	private String description;

	private Location location;


}
