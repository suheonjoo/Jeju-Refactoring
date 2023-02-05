package com.capstone.jejuRefactoring.domain.spot;

import java.util.Arrays;
import java.util.List;

import com.capstone.jejuRefactoring.common.exception.spot.LocationGroupNotFoundException;

/**
 * 북 : 애월읍,제주시,조천읍,구좌읍,우도면
 * 동 : 남원읍, 표선면, 성산읍
 * 서 : 한림읍, 한경면, 대정읍, 안덕면
 * 남 : 서귀포시
 */
public enum LocationGroup {

	NORTH_LOCATION(List.of(Location.Aewol_eup, Location.Jeju_si, Location.Jocheon_eup, Location.Gujwa_eup, Location.Udo_myeon, Location.Chuja_myeon), "북부"),
	EAST_LOCATION(List.of(Location.Namwon_eup, Location.Pyoseon_myeon, Location.Seongsan_eup), "동부"),
	WEST_LOCATION(List.of(Location.Hallim_eup, Location.Hangyeong_myeon, Location.Daejeong_eup, Location.Andeok_myeon), "서부"),
	SOUTH_LOCATION(List.of(Location.Seogwipo_si), "남부"),
	ALL_LOCATION(List.of(Location.Jeju_si, Location.Aewol_eup, Location.Hallim_eup,
		Location.Hangyeong_myeon, Location.Jocheon_eup, Location.Gujwa_eup,
		Location.Daejeong_eup, Location.Andeok_myeon, Location.Seogwipo_si,
		Location.Namwon_eup, Location.Pyoseon_myeon, Location.Seongsan_eup, Location.Udo_myeon, Location.Chuja_myeon), "전체");


	private final List<Location> locations;
	private final String KrDirection;

	LocationGroup(List<Location> locations, String krDirection) {
		this.locations = locations;
		this.KrDirection = krDirection;
	}

	public static List<Location> getLocations(String krName) {
		return Arrays.stream(LocationGroup.values())
			.filter(i -> i.KrDirection.equals(krName))
			.map(i -> i.locations).findFirst().orElseThrow(() -> new LocationGroupNotFoundException());
	}

}