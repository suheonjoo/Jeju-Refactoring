package com.capstone.jejuRefactoring.unit.domain.preference.service;

import static com.capstone.jejuRefactoring.support.helper.PreferenceGivenHelper.*;
import static com.capstone.jejuRefactoring.support.helper.SpotGivenHelper.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.redisson.RedissonLock;
import org.redisson.RedissonReadLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import com.capstone.jejuRefactoring.domain.preference.MemberSpotTag;
import com.capstone.jejuRefactoring.domain.preference.Score;
import com.capstone.jejuRefactoring.domain.preference.dto.request.PriorityWeightDto;
import com.capstone.jejuRefactoring.domain.preference.dto.response.LikeFlipResponse;
import com.capstone.jejuRefactoring.domain.preference.dto.response.SpotIdsWithPageInfoDto;
import com.capstone.jejuRefactoring.domain.preference.repository.MemberSpotTagRepository;
import com.capstone.jejuRefactoring.domain.preference.repository.ScoreRepository;
import com.capstone.jejuRefactoring.domain.preference.repository.SpotLikeTagRepository;
import com.capstone.jejuRefactoring.domain.preference.service.PreferenceService;
import com.capstone.jejuRefactoring.domain.spot.Category;
import com.capstone.jejuRefactoring.domain.spot.Location;
import com.capstone.jejuRefactoring.domain.spot.dto.response.SpotResponse;
import com.capstone.jejuRefactoring.domain.wishList.repository.WishListRepository;
import com.capstone.jejuRefactoring.domain.wishList.repository.WishListSpotTagRepository;
import com.capstone.jejuRefactoring.domain.wishList.service.WishListService;
import com.capstone.jejuRefactoring.infrastructure.preference.dto.ScoreWithSpotLocationDto;
import com.mysema.commons.lang.Assert;

@ExtendWith(MockitoExtension.class)
public class PreferenceServiceTest {

	@InjectMocks
	PreferenceService preferenceService;
	@Mock
	MemberSpotTagRepository memberSpotTagRepository;
	@Mock
	ScoreRepository scoreRepository;
	@Mock
	RedissonClient redissonClient;
	@Mock
	SpotLikeTagRepository spotLikeTagRepository;



	@Test
	public void 회원_관광지_연관관계_테이블_만들기() throws Exception {
		//when
		preferenceService.createMemberSpotTags(1l, List.of(1l, 2l, 3l));

		//then
		then(memberSpotTagRepository).should().saveAll(any());
	}

	@Test
	public void 장소별로_가장_높은_카테고리점수_를_가진_카테고리_가져오기() throws Exception {
		//given
		List<ScoreWithSpotLocationDto> scoreWithSpotLocationDtos = List.of(givenScoreWithSpotLocationDto(1l, 1d),
			givenScoreWithSpotLocationDto(1l, 1d),
			givenScoreWithSpotLocationDto(1l, 1d));
		given(scoreRepository.findScoreBySpotLocations(anyList())).willReturn(scoreWithSpotLocationDtos);

		//when
		Category result = preferenceService.getHighestCategoryByLocations(
			List.of(Location.Aewol_eup));

		//then
		assertThat(result).isEqualTo(Category.SURROUND);
	}

	@Test
	public void 회원이_입력한_우선순위_기반으로_관광지점수_업데이트하기() throws Exception {
		//given
		List<MemberSpotTag> memberSpotTags = List.of(givenMemberSpotTagWithPersonalScore(1l, 1l, 1d),
			givenMemberSpotTagWithPersonalScore(2l, 1l, 2d),
			givenMemberSpotTagWithPersonalScore(3l, 1l, 3d)
		);
		PriorityWeightDto priorityWeightDto = givenPriorityWeightDto(1, 1, 1, 1);
		List<Long> spotIds = List.of(1l, 2l, 3l);
		given(memberSpotTagRepository.findByMemberIdAndSpotIds(any(), anyList())).willReturn(memberSpotTags);

		//when
		SpotIdsWithPageInfoDto result = preferenceService.updateMemberSpotScore(1l, spotIds,
			priorityWeightDto, PageRequest.of(0, 20));

		//then
		assertThat(result.isHasNext()).isFalse();
		assertThat(result.getSpotIds()).isEqualTo(List.of(3l,2l,1l));

	}

	@Test
	public void 특정_관광지점수_가져오기() throws Exception {
		//given
		given(scoreRepository.findBySpotId(any())).willReturn(givenScoreWithSameScore(1l));

		//when
		SpotResponse result = preferenceService.getScoreBySpotId(SpotResponse.from(givenSpotWithId(1l)), 1l);

		//then
		assertThat(result.getScoreResponse().getFacilityScore()).isEqualTo(10d);
		assertThat(result.getScoreResponse().getPriceScore()).isEqualTo(10d);
		assertThat(result.getScoreResponse().getSurroundScore()).isEqualTo(10d);
		assertThat(result.getScoreResponse().getViewScore()).isEqualTo(10d);
		assertThat(result.getScoreResponse().getFacilityRank()).isEqualTo(1d);
		assertThat(result.getScoreResponse().getPriceRank()).isEqualTo(1d);
		assertThat(result.getScoreResponse().getSurroundRank()).isEqualTo(1d);
		assertThat(result.getScoreResponse().getViewRank()).isEqualTo(1d);
		assertThat(result.getId()).isEqualTo(1l);
	}

	@Test
	public void 관광지_좋아요_버튼_누르기() throws Exception {
		//given
		// given(memberSpotTagRepository.isSpotLikExistByMemberIdAndSpotId(any(), any())).willReturn(true);
		// given(spotLikeTagRepository.findBySpotId(any())).willReturn(Optional.of(givenSpotLikeTag(1l)));
		// RLock lock = redissonClient.getLock("1");
		//
		// given(redissonClient.getLock(any())).willReturn(lock);
		//
		// //when
		// LikeFlipResponse result = preferenceService.flipSpotLike(1l, 1l, 1);
		//
		//
		// //then
		// assertThat(result.getLikeCount()).isEqualTo(1);
		// assertThat(result.isLike()).isFalse();
	}

}
