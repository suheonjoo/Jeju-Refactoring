package com.capstone.jejuRefactoring.support.helper;

import static com.capstone.jejuRefactoring.support.helper.PreferenceGivenHelper.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import com.capstone.jejuRefactoring.domain.preference.Score;
import com.capstone.jejuRefactoring.domain.preference.dto.response.SpotIdsWithPageInfoDto;
import com.capstone.jejuRefactoring.domain.spot.Category;
import com.capstone.jejuRefactoring.domain.spot.Location;
import com.capstone.jejuRefactoring.domain.spot.PictureTag;
import com.capstone.jejuRefactoring.domain.spot.Spot;
import com.capstone.jejuRefactoring.domain.spot.dto.response.SpotForRouteRecommendDto;
import com.capstone.jejuRefactoring.domain.spot.dto.response.SpotForRouteRecommendResponse;
import com.capstone.jejuRefactoring.domain.spot.dto.response.SpotPageResponse;
import com.capstone.jejuRefactoring.domain.spot.dto.response.SpotPageWithPictureTagsResponse;
import com.capstone.jejuRefactoring.domain.spot.dto.response.SpotResponse;
import com.capstone.jejuRefactoring.domain.spot.dto.response.SpotWithCategoryScoreAndPictureTagUrlDto;
import com.capstone.jejuRefactoring.domain.spot.dto.response.SpotWithPictureTagsDto;
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

	public static SpotResponse givenSpotResponse(Score score) {
		return SpotResponse.builder()
			.id(1l)
			.name("한라산")
			.address("주소1")
			.location(Location.Aewol_eup)
			.description("설명1")
			.scoreResponse(givenScoreResponse(score))
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

	public static SpotIdsWithPageInfoDto givenSpotIdsWithPageInfoDto(boolean hasNext, List<Long> spotIds) {
		return SpotIdsWithPageInfoDto.builder()
			.hasNext(hasNext)
			.spotIds(spotIds)
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

	public static SpotPageWithPictureTagsResponse givenSpotPageWithPictureTagsResponse(boolean hasNext, int count) {
		List<SpotWithPictureTagsDto> spotWithPictureTagsDtos = new ArrayList<>();
		LongStream.range(1l, count + 1l).forEach(i -> spotWithPictureTagsDtos.add(givenSpotWithPictureTagsDto(i)));
		// List<SpotWithPictureTagsDto> spotWithPictureTagsDtos = List.of(givenSpotWithPictureTagsDto(),
		// 	givenSpotWithPictureTagsDto(), givenSpotWithPictureTagsDto());

		return SpotPageWithPictureTagsResponse.builder()
			.hasNext(hasNext)
			.spotWithPictureTagsDtos(spotWithPictureTagsDtos)
			.build();
	}

	public static SpotWithPictureTagsDto givenSpotWithPictureTagsDto(Long spotId) {
		return SpotWithPictureTagsDto.builder()
			.id(spotId)
			.build();
	}

	public static SpotForRouteRecommendResponse givenSpotForRouteRecommendResponse() {
		List<SpotForRouteRecommendDto> spotForRouteRecommendDtos = List.of(givenSpotForRouteRecommendDto(),
			givenSpotForRouteRecommendDto(),givenSpotForRouteRecommendDto());

		return SpotForRouteRecommendResponse.builder()
			.category(Category.VIEW)
			.spotForRouteRecommendDtos(spotForRouteRecommendDtos)
			.build();
	}

}
