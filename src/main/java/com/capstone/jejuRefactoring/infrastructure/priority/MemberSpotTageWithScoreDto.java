package com.capstone.jejuRefactoring.infrastructure.priority;

import com.capstone.jejuRefactoring.domain.priority.MemberSpotTag;
import com.capstone.jejuRefactoring.domain.priority.Score;

import lombok.Data;

@Data
public class MemberSpotTageWithScoreDto {

	private Long spotId;
	private MemberSpotTag memberSpotTag;
	private Score score;


}
