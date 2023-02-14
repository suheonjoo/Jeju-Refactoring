package com.capstone.jejuRefactoring.infrastructure.priority.dto;

import com.capstone.jejuRefactoring.domain.priority.MemberSpotTag;
import com.capstone.jejuRefactoring.domain.priority.Score;

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
