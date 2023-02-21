package com.capstone.jejuRefactoring.infrastructure.preference.dto;

import com.capstone.jejuRefactoring.domain.preference.MemberSpotTag;
import com.capstone.jejuRefactoring.domain.preference.Score;

import lombok.Data;

@Data
public class MemberSpotTageWithScoreDto {

	private Long spotId;
	private MemberSpotTag memberSpotTag;
	private Score score;

	public MemberSpotTageWithScoreDto(Long spotId, MemberSpotTag memberSpotTag,
		Score score) {
		this.spotId = spotId;
		this.memberSpotTag = memberSpotTag;
		this.score = score;
	}
}
