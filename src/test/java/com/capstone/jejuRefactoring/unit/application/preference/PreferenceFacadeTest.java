package com.capstone.jejuRefactoring.unit.application.preference;

import static com.capstone.jejuRefactoring.support.helper.PreferenceGivenHelper.*;
import static com.capstone.jejuRefactoring.support.helper.SpotGivenHelper.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import com.capstone.jejuRefactoring.application.preference.PreferenceFacade;
import com.capstone.jejuRefactoring.common.exception.spot.LocationGroupNotFoundException;
import com.capstone.jejuRefactoring.common.exception.spot.LocationNotFoundException;
import com.capstone.jejuRefactoring.domain.preference.dto.response.LikeFlipResponse;
import com.capstone.jejuRefactoring.domain.preference.dto.response.SpotIdsWithPageInfoDto;
import com.capstone.jejuRefactoring.domain.preference.service.PreferenceService;
import com.capstone.jejuRefactoring.domain.spot.Category;
import com.capstone.jejuRefactoring.domain.spot.Location;
import com.capstone.jejuRefactoring.domain.spot.dto.response.SpotForRouteRecommendResponse;
import com.capstone.jejuRefactoring.domain.spot.dto.response.SpotPageWithPictureTagsResponse;
import com.capstone.jejuRefactoring.domain.spot.service.SpotService;

@ExtendWith(MockitoExtension.class)
public class PreferenceFacadeTest {

	@InjectMocks
	PreferenceFacade preferenceFacade;
	@Mock
	SpotService spotService;
	@Mock
	PreferenceService preferenceService;

	@Test
	public void 사용자_가중치_점수_기반으로_순위별로_사진과_함께_관광지_정보_가져오기() throws Exception {
		//given
		List<Long> spotIds = List.of(1l, 2l, 3l);
		given(spotService.getBySpotLocations(anyList())).willReturn(spotIds);
		given(preferenceService.updateMemberSpotScore(any(), anyList(), any(), any()))
			.willReturn(givenSpotIdsWithPageInfoDto(true, spotIds));
		given(spotService.getSpotWithPictureTagLimit3(any(), anyList()))
			.willReturn(givenSpotPageWithPictureTagsResponse(true, 3));

		//when
		SpotPageWithPictureTagsResponse result = preferenceFacade.getSpotWithPictureTagsOrderByRank(
			1l, givenPriorityWeightDto(), "북부", PageRequest.of(0, 3));

		//then
		assertThat(result.isHasNext()).isTrue();
		assertThat(result.getSpotWithPictureTagsDtos().size()).isEqualTo(3);
	}

	@Test
	public void 올바른_direction이_아니면_예외발생() throws Exception {
		//given

		//when then
		assertThatThrownBy(
			() -> preferenceFacade.getSpotWithPictureTagsOrderByRank(1l, givenPriorityWeightDto(), "몰랑",
				PageRequest.of(0, 3)))
			.isInstanceOf(LocationGroupNotFoundException.class);
	}

	@Test
	public void 지역마다_순위대로_Top10_관광지정보_사진과_함께_가져오기() throws Exception {
		//given
		given(preferenceService.getHighestCategoryByLocations(anyList()))
			.willReturn(Category.VIEW);
		given(spotService.getSpotWithPictureTagPerLocations(anyList(), any()))
			.willReturn(givenSpotForRouteRecommendResponse());

		//when
		SpotForRouteRecommendResponse result = preferenceFacade.getTenSpotsWithPictureTagsOrderByRankPerLocations(
			List.of("제주시","애월읍"));

		//then
		assertThat(result.getCategory()).isEqualTo(Category.VIEW);
		assertThat(result.getSpotForRouteRecommendDtos().size()).isEqualTo(3);
	}

	@Test
	public void 존재하지_않은_Location인경우_예외발생() throws Exception{
		//given

		//when then
		assertThatThrownBy(
			() -> preferenceFacade.getTenSpotsWithPictureTagsOrderByRankPerLocations(List.of("몰랑")))
			.isInstanceOf(LocationNotFoundException.class);
	}

	@Test
	public void 관광지_좋아요_누르기() throws Exception {
		//given
		given(preferenceService.flipSpotLike(any(), any(), any()))
			.willReturn(givenLikeFlipResponse(true,1));

		//when
		LikeFlipResponse result = preferenceFacade.flipSpotLike(1l, 1l);

		//then
		assertThat(result.isLike()).isTrue();
		assertThat(result.getLikeCount()).isEqualTo(1);
	}

}
