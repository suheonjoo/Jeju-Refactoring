package com.capstone.jejuRefactoring.unit.domain.spot.service;

import static com.capstone.jejuRefactoring.support.helper.SpotGivenHelper.*;
import static com.capstone.jejuRefactoring.support.helper.WishListGivenHelper.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

import com.capstone.jejuRefactoring.common.support.RepositorySupport;
import com.capstone.jejuRefactoring.domain.spot.Category;
import com.capstone.jejuRefactoring.domain.spot.Location;
import com.capstone.jejuRefactoring.domain.spot.PictureTag;
import com.capstone.jejuRefactoring.domain.spot.Spot;
import com.capstone.jejuRefactoring.domain.spot.dto.response.SpotForRouteRecommendDto;
import com.capstone.jejuRefactoring.domain.spot.dto.response.SpotForRouteRecommendResponse;
import com.capstone.jejuRefactoring.domain.spot.dto.response.SpotPageResponse;
import com.capstone.jejuRefactoring.domain.spot.dto.response.SpotPageWithPictureTagsResponse;
import com.capstone.jejuRefactoring.domain.spot.dto.response.SpotResponse;
import com.capstone.jejuRefactoring.domain.spot.dto.response.SpotsForRouteDto;
import com.capstone.jejuRefactoring.domain.spot.dto.response.WishListsWithPictureTagsResponseDto;
import com.capstone.jejuRefactoring.domain.spot.repository.PictureTagRepository;
import com.capstone.jejuRefactoring.domain.spot.repository.SpotRepository;
import com.capstone.jejuRefactoring.domain.spot.service.SpotService;
import com.capstone.jejuRefactoring.infrastructure.spot.dto.PictureTagUrlDto;
import com.capstone.jejuRefactoring.infrastructure.spot.dto.SpotWithCategoryScoreDto;

@ExtendWith(MockitoExtension.class)
public class SpotServiceTest {

	@InjectMocks
	SpotService spotService;
	@Mock
	SpotRepository spotRepository;
	@Mock
	PictureTagRepository pictureTagRepository;

	@Test
	public void spotId로_관광지정보_가져오기() throws Exception {

		//given
		Spot spot = givenSpot();
		given(spotRepository.findById(any())).willReturn(Optional.of(spot));

		// when
		SpotResponse result = spotService.getBySpotId(any());

		// then
		assertThat(result.getName()).isEqualTo(spot.getName());
		assertThat(result.getAddress()).isEqualTo(spot.getAddress());
		assertThat(result.getDescription()).isEqualTo(spot.getDescription());
		assertThat(result.getLocation()).isEqualTo(spot.getLocation());
		assertThat(result.getScoreResponse()).isEqualTo(null);
	}

	@Test
	public void 제주도_지역마다_관광지정보와_그에_해당하는_사진_가져오기() throws Exception {
		//given
		List<SpotWithCategoryScoreDto> spotWithCategoryScoreDtos = List.of(givenSpotWithCategoryScoreDto(1l));
		List<PictureTagUrlDto> pictureTagUrlDtos = List.of(givenPictureTagUrlDto(1l, 2l));

		given(spotRepository.findWithCategoryScoreByLocation(List.of(Location.Aewol_eup), Category.VIEW))
			.willReturn(spotWithCategoryScoreDtos);
		given(pictureTagRepository.findNPictureTagForSpotIds(List.of(1l), 1))
			.willReturn(pictureTagUrlDtos);

		//when
		SpotForRouteRecommendResponse result = spotService.getSpotWithPictureTagPerLocations(
			List.of(Location.Aewol_eup), Category.VIEW);

		System.out.println(result.getSpotForRouteRecommendDtos().size());

		//then
		assertThat(result.getSpotForRouteRecommendDtos().get(0).getLocation()).isEqualTo(
			spotWithCategoryScoreDtos.get(0).getLocation());
		assertThat(result.getSpotForRouteRecommendDtos().get(0).getSpotWithCategoryScoreAndPictureTagUrlDtos().get(0)
			.getId()).isEqualTo(spotWithCategoryScoreDtos.get(0).getId());
		assertThat(result.getSpotForRouteRecommendDtos()
			.get(0)
			.getSpotWithCategoryScoreAndPictureTagUrlDtos()
			.get(0)
			.getName()).isEqualTo(
			spotWithCategoryScoreDtos.get(0).getName());

		assertThat(result.getSpotForRouteRecommendDtos()
			.get(0)
			.getSpotWithCategoryScoreAndPictureTagUrlDtos()
			.get(0)
			.getAddress()).isEqualTo(spotWithCategoryScoreDtos.get(0).getAddress());

		assertThat(result.getSpotForRouteRecommendDtos().get(0).getSpotWithCategoryScoreAndPictureTagUrlDtos()
			.get(0).getDescription()).isEqualTo(spotWithCategoryScoreDtos.get(0).getDescription());

		assertThat(result.getSpotForRouteRecommendDtos().get(0)
			.getSpotWithCategoryScoreAndPictureTagUrlDtos().get(0)
			.getCategoryScore()).isEqualTo(spotWithCategoryScoreDtos.get(0).getCategoryScore());

		assertThat(result.getSpotForRouteRecommendDtos().get(0)
			.getSpotWithCategoryScoreAndPictureTagUrlDtos().get(0).getPictureUrl().getUrl()).isEqualTo(
			pictureTagUrlDtos.get(0).getUrl());

	}

	@Test
	public void 모든_관광지의_아이디_가져오기() throws Exception {
		//given
		given(spotRepository.findAllSpotIds()).willReturn(List.of(1l));

		//when
		List<Long> result = spotService.getAllSpotIds();

		//then
		assertThat(result).isEqualTo(List.of(1l));
	}

	@Test
	public void 제주도지역들에_있는_관광지_아이디_가져오기() throws Exception {
		//given
		given(spotRepository.findSpotIdsByLocations(anyList())).willReturn(List.of(1l));

		//when
		List<Long> result = spotService.getBySpotLocations(anyList());

		//then
		assertThat(result).isEqualTo(List.of(1l));
	}

	@Test
	public void 페이징된_관광지정보를_사진3개와_함께_가져오기() throws Exception {
		//given

		given(spotRepository.findByLocationsAndSpotIds(List.of(1l), List.of(Location.Aewol_eup))).willReturn(
			List.of(givenSpotWithId(1l))
		);
		given(pictureTagRepository.findBySpotIds(anyList())).willReturn(
			List.of(givenPictureTag(1l), givenPictureTag(1l), givenPictureTag(1l))
		);

		//when
		SpotPageWithPictureTagsResponse result = spotService.getSpotWithPictureTagLimit3(givenSpotIdsWithPageInfoDto() ,
			List.of(Location.Aewol_eup));

		//then
		assertThat(result.isHasNext()).isFalse();
		assertThat(result.getSpotWithPictureTagsDtos().get(0).getId()).isEqualTo(1l);
		assertThat(result.getSpotWithPictureTagsDtos().get(0).getName()).isEqualTo("한라산");
		assertThat(result.getSpotWithPictureTagsDtos().get(0).getAddress()).isEqualTo("주소1");
		assertThat(result.getSpotWithPictureTagsDtos().get(0).getDescription()).isEqualTo("설명1");
		assertThat(result.getSpotWithPictureTagsDtos().get(0).getLocation()).isEqualTo(Location.Aewol_eup);
		assertThat(result.getSpotWithPictureTagsDtos().get(0).getPictureTagResponses().size()).isEqualTo(3);
		assertThat(result.getSpotWithPictureTagsDtos().get(0).getPictureTagResponses().get(0).getSpotId()).isEqualTo(1l);
		assertThat(result.getSpotWithPictureTagsDtos().get(0).getPictureTagResponses().get(0).getUrl()).isEqualTo("pictureTagURL1");
	}

	@Test
	public void 경로상에_있는_관광지_정보를_사진1장과_함께_가져오기() throws Exception {
		//given
		;
		given(spotRepository.findBySpotIdsWithFetchJoin(List.of(1l,2l,3l)))
			.willReturn(
				List.of(givenSpotWithPictureTags(1l), givenSpotWithPictureTags(2l), givenSpotWithPictureTags(3l))
			);

		//when
		SpotsForRouteDto result = spotService.getSpotInfoBySpotIdsForRoute(
			givenWishListSpotIdsResponseDto(List.of(1l, 2l, 3l)));

		//then
		assertThat(result.getWishListId()).isEqualTo(1l);
		assertThat(result.getSpotWithPictureTagDtos().size()).isEqualTo(3);
		assertThat(result.getSpotWithPictureTagDtos().get(0).getName()).isEqualTo("한라산");
		assertThat(result.getSpotWithPictureTagDtos().get(0).getAddress()).isEqualTo("주소1");
		assertThat(result.getSpotWithPictureTagDtos().get(0).getDescription()).isEqualTo("설명1");
		assertThat(result.getSpotWithPictureTagDtos().get(0).getPictureTagDto().getSpotId()).isEqualTo(1l);
		assertThat(result.getSpotWithPictureTagDtos().get(0).getPictureTagDto().getUrl()).isEqualTo("pictureTagURL1");

	}

	@Test
	public void 위시리시트페이지에서_각_위시리스트마다_보여지는_관광지사진_보여주기() throws Exception {
		//given
		List<PictureTagUrlDto> pictureTagUrlDtos = List.of(
			givenPictureTagUrlDto(1l, 1l), givenPictureTagUrlDto(2l, 2l), givenPictureTagUrlDto(3l, 3l)
		);

		given(pictureTagRepository.findNPictureTagForSpotIds(List.of(1l, 2l, 3l), 3))
			.willReturn(pictureTagUrlDtos);
		given(pictureTagRepository.findNPictureTagForSpotIds(List.of(1l, 2l, 3l), 3))
			.willReturn(pictureTagUrlDtos);
		given(pictureTagRepository.findNPictureTagForSpotIds(List.of(1l, 2l, 3l), 3))
			.willReturn(pictureTagUrlDtos);

		//when
		WishListsWithPictureTagsResponseDto result = spotService.getPictureTagsForWishLists(
			givenWishListsResponseDto());

		//then
		assertThat(result.getWishListResponseDtos().get(0).getWishListName()).isEqualTo("위시리스트1");
		assertThat(result.getWishListResponseDtos().size()).isEqualTo(3);
		assertThat(result.getWishListResponseDtos().get(1).getPictureTagUrlDtos().get(0).getSpotId()).isEqualTo(1l);
		assertThat(result.getWishListResponseDtos().get(1).getPictureTagUrlDtos().get(0).getUrl()).isEqualTo("url1");
		assertThat(result.getWishListResponseDtos().get(1).getPictureTagUrlDtos().get(0).getIdMin()).isEqualTo(1l);
		assertThat(result.getWishListResponseDtos().get(1).getPictureTagUrlDtos().size()).isEqualTo(3);
	}

	@Test
	public void 사용자가_검색한_키워드_기반으로_페이징된_관광지를_사진과_함께_가져오기() throws Exception {
		//given
		List<PictureTag> pictureTags = List.of(
			givenPictureTag(1l), givenPictureTag(1l), givenPictureTag(1l),
			givenPictureTag(1l)
		);

		List<SpotPageResponse> spotPageResponses = List.of(givenSpotPageResponseWithId(1l), givenSpotPageResponseWithId(2l),
			givenSpotPageResponseWithId(3l));
		PageRequest pageRequest = PageRequest.of(0, 2);
		Slice<SpotPageResponse> slice = RepositorySupport.toSlice(spotPageResponses, pageRequest);
		given(spotRepository.findPageBySpotName("관광지", 1l, pageRequest))
			.willReturn(slice);
		given(pictureTagRepository.findBySpotIds(List.of(1l,2l)))
			.willReturn(pictureTags);

		//when
		SpotPageWithPictureTagsResponse result = spotService.getSpotsBySpotName("관광지", 1l, pageRequest);

		//then
		assertThat(result.isHasNext()).isTrue();
		assertThat(result.getSpotWithPictureTagsDtos().size()).isEqualTo(2);
		assertThat(result.getSpotWithPictureTagsDtos().get(0).getName()).isEqualTo("관광지1");
		assertThat(result.getSpotWithPictureTagsDtos().get(0).getAddress()).isEqualTo("주소1");
		assertThat(result.getSpotWithPictureTagsDtos().get(0).getLocation()).isEqualTo(Location.Aewol_eup);
	}

}



