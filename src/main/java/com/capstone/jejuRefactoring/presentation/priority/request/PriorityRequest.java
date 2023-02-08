package com.capstone.jejuRefactoring.presentation.priority.request;

import com.capstone.jejuRefactoring.domain.priority.dto.request.PriorityWeightDto;

import lombok.Data;

@Data
public class PriorityRequest {

	private String direction;
	private Integer viewWeight;
	private Integer priceWeight;
	private Integer facilityWeight;
	private Integer surroundWeight;
	private boolean isSameWeight;

	public PriorityWeightDto toPriorityWeightDto() {
		return PriorityWeightDto.builder()
			.viewWeight(viewWeight)
			.priceWeight(priceWeight)
			.facilityWeight(facilityWeight)
			.surroundWeight(surroundWeight)
			.isSameWeight(isSameWeight)
			.build();
	}

}
