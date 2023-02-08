package com.capstone.jejuRefactoring.domain.priority.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capstone.jejuRefactoring.domain.priority.MemberSpotTag;
import com.capstone.jejuRefactoring.domain.priority.Score;
import com.capstone.jejuRefactoring.domain.priority.dto.request.PriorityWeightDto;
import com.capstone.jejuRefactoring.domain.priority.repository.MemberSpotTagRepository;
import com.capstone.jejuRefactoring.domain.priority.repository.ScoreRepository;
import com.capstone.jejuRefactoring.domain.spot.Category;
import com.capstone.jejuRefactoring.domain.spot.Location;
import com.capstone.jejuRefactoring.domain.spot.dto.response.ScoreResponse;
import com.capstone.jejuRefactoring.domain.spot.dto.response.SpotResponse;
import com.capstone.jejuRefactoring.infrastructure.priority.ScoreWithSpotLocationDto;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PriorityService {

	private final MemberSpotTagRepository memberSpotTagRepository;
	private final ScoreRepository scoreRepository;

	public void createMemberSpotTags(Long memberId, List<Long> spotIds) {
		List<MemberSpotTag> memberSpotTags = spotIds.stream()
			.map(spotId -> MemberSpotTag.of(memberId, spotId))
			.collect(Collectors.toList());
		memberSpotTagRepository.saveAll(memberSpotTags);
	}

	public Category getHighestCategoryByLocations(List<Location> locations) {

		List<ScoreWithSpotLocationDto> scoreBySpotLocationDtos = scoreRepository.findScoreBySpotLocations(locations);
		double view = 0, price = 0, facility = 0, surround = 0;
		return getHighestCategory(scoreBySpotLocationDtos, view, price, facility, surround);

	}

	private Category getHighestCategory(List<ScoreWithSpotLocationDto> scoreBySpotLocationDtos, double view, double price,
		double facility, double surround) {
		for (ScoreWithSpotLocationDto scoreBySpotLocationDto : scoreBySpotLocationDtos) {
			view += scoreBySpotLocationDto.getViewScore();
			price += scoreBySpotLocationDto.getPriceScore();
			facility += scoreBySpotLocationDto.getFacilityScore();
			surround += scoreBySpotLocationDto.getSurroundScore();
		}
		double max = Math.max(view, Math.max(Math.max(price, facility), surround));
		if (max == view)
			return Category.VIEW;
		else if (max == price)
			return Category.PRICE;
		else if (max == facility)
			return Category.FACILITY;
		else if (max == surround)
			return Category.SURROUND;
		else
			return Category.ALL;
	}

	public List<Long> updateMemberSpotScore(Long memberId, List<Long> spotIds, PriorityWeightDto priorityWeightDto) {
		//member
		List<MemberSpotTag> memberSpotTags = memberSpotTagRepository.findByMemberIdAndSpotIds(memberId, spotIds);

		Map<Long, List<Score>> scoresBySpotIdMap = scoreRepository.findBySpotIds(spotIds)
			.stream()
			.collect(Collectors.groupingBy(score -> score.getSpot().getId()));

		for (MemberSpotTag memberSpotTag : memberSpotTags) {
			Score score = scoresBySpotIdMap.get(memberSpotTag.getSpot().getId()).get(0);
			Double memberPersonalScore = calculatePersonalScore(priorityWeightDto, score);
			memberSpotTag.update(memberPersonalScore);
		}
		Collections.sort(memberSpotTags);
		return memberSpotTags.stream()
			.map(memberSpotTag -> memberSpotTag.getSpot().getId())
			.collect(Collectors.toList());
	}

	private Double calculatePersonalScore(PriorityWeightDto priorityWeightDto, Score score) {
		Double viewWeight = priorityWeightDto.getViewWeight().doubleValue();
		Double priceWeight = priorityWeightDto.getPriceWeight().doubleValue();
		Double facilityWeight = priorityWeightDto.getFacilityWeight().doubleValue();
		Double surroundWeight = priorityWeightDto.getSurroundWeight().doubleValue();
		Double memberPersonalScore = (score.getViewScore().doubleValue() * viewWeight +
			score.getPriceScore().doubleValue() * priceWeight +
			score.getFacilityScore().doubleValue() * facilityWeight +
			score.getSurroundScore() * surroundWeight) / (viewWeight + priceWeight + facilityWeight
			+ surroundWeight);
		return memberPersonalScore;
	}

	public SpotResponse getScoreBySpotId(SpotResponse spotBySpotId, Long spotId) {
		return SpotResponse.of(spotBySpotId, ScoreResponse.from(scoreRepository.findBySpotId(spotId)));
	}
}
