package com.capstone.jejuRefactoring.infrastructure.preference.dto;

import com.capstone.jejuRefactoring.domain.preference.MemberSpotTag;
import com.capstone.jejuRefactoring.domain.preference.Score;

import lombok.Data;

@Data
public class MemberSpotTagWithScoreDto {

	private Long spotId;
	private MemberSpotTag memberSpotTag;
	private Score score;

	public MemberSpotTagWithScoreDto(Long spotId, MemberSpotTag memberSpotTag,
		Score score) {
		this.spotId = spotId;
		this.memberSpotTag = memberSpotTag;
		this.score = score;
	}
}
