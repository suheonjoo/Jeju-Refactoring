package com.capstone.jejuRefactoring.domain.spot.dto.response;

import com.capstone.jejuRefactoring.domain.preference.Score;

import lombok.Getter;


@Getter
public class ScoreResponse {

	private Double viewScore;
	private Double priceScore;
	private Double facilityScore;
	private Double surroundScore;

	private Double viewRank;
	private Double priceRank;
	private Double facilityRank;
	private Double surroundRank;

	private Double rankAverage;

	private ScoreResponse(Double viewScore, Double priceScore, Double facilityScore, Double surroundScore,
		Double viewRank, Double priceRank, Double facilityRank, Double surroundRank, Double rankAverage) {
		this.viewScore = viewScore;
		this.priceScore = priceScore;
		this.facilityScore = facilityScore;
		this.surroundScore = surroundScore;
		this.viewRank = viewRank;
		this.priceRank = priceRank;
		this.facilityRank = facilityRank;
		this.surroundRank = surroundRank;
		this.rankAverage = rankAverage;
	}

	public static ScoreResponse from(final Score score) {
		return new ScoreResponse(score.getViewScore(), score.getPriceScore(), score.getFacilityScore(),
			score.getSurroundScore(),
			score.getViewRank(), score.getPriceRank(), score.getFacilityRank(), score.getSurroundRank(),
			score.getRankAverage());
	}

}
