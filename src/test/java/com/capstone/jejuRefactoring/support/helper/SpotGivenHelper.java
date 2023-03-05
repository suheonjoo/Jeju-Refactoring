package com.capstone.jejuRefactoring.support.helper;

import java.util.List;

import org.springframework.data.domain.Slice;

import com.capstone.jejuRefactoring.domain.preference.dto.response.SpotIdsWithPageInfoDto;
import com.capstone.jejuRefactoring.domain.spot.Category;
import com.capstone.jejuRefactoring.domain.spot.Location;
import com.capstone.jejuRefactoring.domain.spot.PictureTag;
import com.capstone.jejuRefactoring.domain.spot.Spot;
import com.capstone.jejuRefactoring.domain.spot.dto.response.SpotForRouteRecommendDto;
import com.capstone.jejuRefactoring.domain.spot.dto.response.SpotForRouteRecommendResponse;
import com.capstone.jejuRefactoring.domain.spot.dto.response.SpotPageResponse;
import com.capstone.jejuRefactoring.domain.spot.dto.response.SpotWithCategoryScoreAndPictureTagUrlDto;
import com.capstone.jejuRefactoring.infrastructure.spot.dto.PictureTagUrlDto;
import com.capstone.jejuRefactoring.infrastructure.spot.dto.SpotWithCategoryScoreDto;

public class SpotGivenHelper {

	public static Spot givenSpot() {
		return Spot.builder()
			.name("한라산")
			.address("주소1")
			.location(Location.Aewol_eup)
			.description("설명1")
			.build();
	}

	public static Spot givenSpotWithId(Long spotId) {
		return Spot.builder()
			.id(spotId)
			.name("한라산")
			.address("주소1")
			.location(Location.Aewol_eup)
			.description("설명1")
			.build();
	}

	public static Spot givenSpotWithPictureTags(Long spotId) {
		List<PictureTag> pictureTags = List.of(givenPictureTag(spotId), givenPictureTag(spotId),
			givenPictureTag(spotId));
		return Spot.builder()
			.name("한라산")
			.address("주소1")
			.location(Location.Aewol_eup)
			.description("설명1")
			.pictureTags(pictureTags)
			.build();
	}

	public static Spot givenSpotWithName(String name) {
		return Spot.builder()
			.name(name)
			.address("주소1")
			.location(Location.Aewol_eup)
			.description("설명1")
			.build();
	}

	public static Spot givenSpotWithLocation(Location location) {
		return Spot.builder()
			.address("주소1")
			.location(location)
			.description("설명1")
			.build();
	}

	public static PictureTag givenPictureTag(Long spotId) {
		return PictureTag.builder()
			.id(1l)
			.url("pictureTagURL1")
			.spot(Spot.builder().id(spotId).build())
			.build();
	}

	public static PictureTag givenPictureTagWithUrl(Long spotId, String url) {
		return PictureTag.builder()
			.url(url)
			.spot(Spot.builder().id(spotId).build())
			.build();
	}

	public static PictureTagUrlDto givenPictureTagUrlDto(Long spotId, Long pictureMinId) {
		return PictureTagUrlDto.builder()
			.spotId(spotId)
			.url("url1")
			.idMin(pictureMinId)
			.build();
	}

	public static SpotIdsWithPageInfoDto givenSpotIdsWithPageInfoDto() {
		return SpotIdsWithPageInfoDto.builder()
			.hasNext(false)
			.spotIds(List.of(1l))
			.build();
	}

	public static SpotWithCategoryScoreDto givenSpotWithCategoryScoreDto(Long spotId) {
		return SpotWithCategoryScoreDto.builder()
			.id(spotId)
			.name("관광지1")
			.description("관광지 설명")
			.address("관광지1 주소")
			.location(Location.Aewol_eup)
			.categoryScore(100d)
			.build();
	}

	public static SpotPageResponse givenSpotPageResponseWithId(Long spotId) {
		return SpotPageResponse.builder()
			.id(spotId)
			.name("관광지1")
			.address("주소1")
			.description("설명1")
			.location(Location.Aewol_eup)
			.build();
	}

	public static SpotWithCategoryScoreAndPictureTagUrlDto givenSpotWithCategoryScoreAndPictureTagUrlDto(Long spotId,
		Long pictureMinId) {
		return SpotWithCategoryScoreAndPictureTagUrlDto.builder()
			.name("관광지1")
			.description("관광지 설명")
			.address("관광지1 주소")
			.location(Location.Aewol_eup)
			.categoryScore(100d)
			.pictureUrl(givenPictureTagUrlDto(spotId, pictureMinId))
			.build();
	}

	public static SpotForRouteRecommendDto givenSpotForRouteRecommendDto() {
		List<SpotWithCategoryScoreAndPictureTagUrlDto> spotWithCategoryScoreAndPictureTagUrlDtos = List.of(
			givenSpotWithCategoryScoreAndPictureTagUrlDto(1l, 1l),
			givenSpotWithCategoryScoreAndPictureTagUrlDto(2l, 2l),
			givenSpotWithCategoryScoreAndPictureTagUrlDto(3l, 3l),
			givenSpotWithCategoryScoreAndPictureTagUrlDto(4l, 4l));

		return SpotForRouteRecommendDto.builder()
			.location(Location.Aewol_eup)
			.spotWithCategoryScoreAndPictureTagUrlDtos(spotWithCategoryScoreAndPictureTagUrlDtos)
			.build();
	}

	public static SpotForRouteRecommendResponse givenSpotForRouteRecommendResponse() {
		List<SpotForRouteRecommendDto> spotForRouteRecommendDtos = List.of(givenSpotForRouteRecommendDto(),
			givenSpotForRouteRecommendDto(),
			givenSpotForRouteRecommendDto(), givenSpotForRouteRecommendDto());

		return SpotForRouteRecommendResponse.builder()
			.category(Category.VIEW)
			.spotForRouteRecommendDtos(spotForRouteRecommendDtos)
			.build();
	}

}
