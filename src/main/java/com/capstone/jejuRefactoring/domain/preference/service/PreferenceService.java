package com.capstone.jejuRefactoring.domain.preference.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.capstone.jejuRefactoring.common.exception.priority.NotLockException;
import com.capstone.jejuRefactoring.domain.preference.MemberSpotTag;
import com.capstone.jejuRefactoring.domain.preference.Score;
import com.capstone.jejuRefactoring.domain.preference.SpotLikeTag;
import com.capstone.jejuRefactoring.domain.preference.dto.request.PriorityWeightDto;
import com.capstone.jejuRefactoring.domain.preference.dto.response.LikeFlipResponse;
import com.capstone.jejuRefactoring.domain.preference.dto.response.MemberSpotTagDto;
import com.capstone.jejuRefactoring.domain.preference.dto.response.SpotIdsWithPageInfoDto;
import com.capstone.jejuRefactoring.domain.preference.repository.MemberSpotTagRepository;
import com.capstone.jejuRefactoring.domain.preference.repository.ScoreRepository;
import com.capstone.jejuRefactoring.domain.preference.repository.SpotLikeTagRepository;
import com.capstone.jejuRefactoring.domain.spot.Category;
import com.capstone.jejuRefactoring.domain.spot.Location;
import com.capstone.jejuRefactoring.domain.spot.dto.response.ScoreResponse;
import com.capstone.jejuRefactoring.domain.spot.dto.response.SpotResponse;
import com.capstone.jejuRefactoring.infrastructure.preference.dto.ScoreWithSpotLocationDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PreferenceService {

	private final MemberSpotTagRepository memberSpotTagRepository;
	private final ScoreRepository scoreRepository;
	private final SpotLikeTagRepository spotLikeTagRepository;

	@Transactional
	public void createMemberSpotTags(Long memberId, List<Long> spotIds) {
		List<MemberSpotTag> memberSpotTags = spotIds.stream()
			.map(spotId -> MemberSpotTag.of(memberId, spotId))
			.collect(Collectors.toList());
		 memberSpotTagRepository.saveAll(memberSpotTags);
	}

	public Category getHighestCategoryByLocations(List<Location> locations) {
		List<ScoreWithSpotLocationDto> scoreBySpotLocationDtos = scoreRepository.findScoreBySpotLocations(locations);
		return getHighestCategory(scoreBySpotLocationDtos);

	}

	private Category getHighestCategory(List<ScoreWithSpotLocationDto> scoreBySpotLocationDtos) {
		double view = 0, price = 0, facility = 0, surround = 0;
		for (ScoreWithSpotLocationDto scoreBySpotLocationDto : scoreBySpotLocationDtos) {
			view += scoreBySpotLocationDto.getViewScore();
			price += scoreBySpotLocationDto.getPriceScore();
			facility += scoreBySpotLocationDto.getFacilityScore();
			surround += scoreBySpotLocationDto.getSurroundScore();
		}
		return getHighestCategoryByMaxScore(view, price, facility, surround);
	}

	private Category getHighestCategoryByMaxScore(double view, double price, double facility, double surround) {
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

	@Transactional
	public SpotIdsWithPageInfoDto updateMemberSpotScore(Long memberId, List<Long> spotIds,
		PriorityWeightDto priorityWeightDto,
		Pageable pageable) {
		List<MemberSpotTag> memberSpotTags = memberSpotTagRepository.findByMemberIdAndSpotIds(memberId, spotIds);
		if (priorityWeightDto.isSameWeight() == true) {
			updateMemberSpotScore(spotIds, priorityWeightDto, memberSpotTags);
		}
		List<MemberSpotTagDto> memberSpotTagDtos = memberSpotTags.stream().map(MemberSpotTagDto::from).collect(
			Collectors.toList());
		Collections.sort(memberSpotTagDtos);
		return getSpotIdsWithPageInfoDto(pageable, memberSpotTagDtos);
	}

	private void updateMemberSpotScore(List<Long> spotIds, PriorityWeightDto priorityWeightDto,
		List<MemberSpotTag> memberSpotTags) {
		Map<Long, List<Score>> scoresBySpotIdMap = scoreRepository.findBySpotIds(spotIds)
			.stream()
			.collect(Collectors.groupingBy(score -> score.getSpot().getId()));
		for (MemberSpotTag memberSpotTag : memberSpotTags) {
			Score score = scoresBySpotIdMap.get(memberSpotTag.getSpot().getId()).get(0);
			Double memberPersonalScore = calculatePersonalScore(priorityWeightDto, score);
			memberSpotTag.update(memberPersonalScore);
		}
	}

	//페이징를 디비에서 하지 않고 필요한 관광지 id만 뽑도록 하였습니다
	private SpotIdsWithPageInfoDto getSpotIdsWithPageInfoDto(Pageable pageable, List<MemberSpotTagDto> memberSpotTagDtos) {
		List<Long> spotIdOrderByScore = memberSpotTagDtos.stream()
			.map(memberSpotTag -> memberSpotTag.getSpotId())
			.skip(pageable.getOffset())
			.limit(pageable.getPageSize())
			.collect(Collectors.toList());
		return SpotIdsWithPageInfoDto.of(hasNext(pageable, memberSpotTagDtos), spotIdOrderByScore);
	}

	private Boolean hasNext(Pageable pageable, List<MemberSpotTagDto> memberSpotTagDtos) {
		if (memberSpotTagDtos.size() > (pageable.getPageNumber() + 1) * pageable.getPageSize()) {
			return true;
		}
		return false;
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

	@Transactional
	public LikeFlipResponse updateSpotLike(Long spotId, Long memberId) {
		MemberSpotTag memberSpotTag = memberSpotTagRepository.findByMemberIdAndSpotIds(memberId,List.of(spotId)).get(0);
		SpotLikeTag spotLikeTag = spotLikeTagRepository.findBySpotId(spotId).get();
		if (memberSpotTag.isSpotLikeExit()) {
			boolean flipSpotLike = memberSpotTag.flipSpotLike();
			Integer likeCount = spotLikeTag.decreaseLikeCount();
			return LikeFlipResponse.of(likeCount, flipSpotLike);
		}
		boolean flipSpotLike = memberSpotTag.flipSpotLike();
		Integer likeCount = spotLikeTag.increaseLikeCount();
		return LikeFlipResponse.of(likeCount, flipSpotLike);
	}


}
