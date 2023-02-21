package com.capstone.jejuRefactoring.domain.preference.dto.response;

import com.capstone.jejuRefactoring.domain.preference.MemberSpotTag;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberSpotTagDto implements Comparable<MemberSpotTagDto>{

	private Long spotId;
	private Double score;

	public static MemberSpotTagDto from(MemberSpotTag memberSpotTag) {
		return MemberSpotTagDto.builder()
			.spotId(memberSpotTag.getSpot().getId())
			.score(memberSpotTag.getPersonalScore())
			.build();
	}

	@Override
	public int compareTo(MemberSpotTagDto o) {
		return (int)(o.score - this.score);
	}
}
