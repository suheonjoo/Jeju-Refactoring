package com.capstone.jejuRefactoring.domain.spot;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.capstone.jejuRefactoring.common.exception.spot.LocationNotFoundException;

import lombok.Getter;

@Getter
public enum Location {
	Jeju_si("제주시"),
	Aewol_eup("애월읍"),
	Hallim_eup("한림읍"),
	Hangyeong_myeon("한경면"),
	Jocheon_eup("조천읍"),
	Gujwa_eup("구좌읍"),
	Daejeong_eup("대정읍"),
	Andeok_myeon("안덕면"),
	Seogwipo_si("서귀포시"),
	Namwon_eup("남원읍"),
	Pyoseon_myeon("표선면"),
	Seongsan_eup("성산읍"),
	Udo_myeon("우도면"),
	Chuja_myeon("추자면");

	private String krName;

	Location(String krName) {
		this.krName = krName;
	}

	public static Location getLocationByKrName(String krName) {
		return Arrays.stream(Location.values())
			.filter(i -> i.krName.equals(krName))
			.findFirst().orElseThrow(() -> new LocationNotFoundException());
	}

	public static List<Location> getLocations(List<String> locations) {
		 return locations.stream()
			.map(location -> Location.getLocationByKrName(location))
			.collect(Collectors.toList());
	}

}

