package com.capstone.jejuRefactoring.domain.preference.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PriorityWeightDto {

	private Integer viewWeight;
	private Integer priceWeight;
	private Integer facilityWeight;
	private Integer surroundWeight;
	private boolean isSameWeight;

}
