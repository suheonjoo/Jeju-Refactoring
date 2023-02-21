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
public class SpotPageResponse {

	private Long id;

	private String name;

	private String address;

	@Lob
	private String description;

	private Location location;

	public SpotPageResponse(Long id, String name, String address, String description,
		Location location) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.description = description;
		this.location = location;
	}
}
