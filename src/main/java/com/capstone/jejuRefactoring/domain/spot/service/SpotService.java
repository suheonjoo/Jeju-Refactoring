package com.capstone.jejuRefactoring.domain.spot.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capstone.jejuRefactoring.common.exception.spot.SpotNotFoundException;
import com.capstone.jejuRefactoring.domain.preference.dto.response.SpotIdsWithPageInfoDto;
import com.capstone.jejuRefactoring.domain.spot.Category;
import com.capstone.jejuRefactoring.domain.spot.Location;
import com.capstone.jejuRefactoring.domain.spot.Spot;
import com.capstone.jejuRefactoring.domain.spot.dto.response.PictureTagDto;
import com.capstone.jejuRefactoring.domain.spot.dto.response.PictureTagResponse;
import com.capstone.jejuRefactoring.domain.spot.dto.response.SpotForRouteRecommendDto;
import com.capstone.jejuRefactoring.domain.spot.dto.response.SpotForRouteRecommendResponse;
import com.capstone.jejuRefactoring.domain.spot.dto.response.SpotPageResponse;
import com.capstone.jejuRefactoring.domain.spot.dto.response.SpotPageWithPictureTagsResponse;
import com.capstone.jejuRefactoring.domain.spot.dto.response.SpotPictureTagForWishListResponseDto;
import com.capstone.jejuRefactoring.domain.spot.dto.response.SpotResponse;
import com.capstone.jejuRefactoring.domain.spot.dto.response.SpotWithPictureTagDto;
import com.capstone.jejuRefactoring.domain.spot.dto.response.SpotWithPictureTagsDto;
import com.capstone.jejuRefactoring.domain.spot.dto.response.SpotsForRouteDto;
import com.capstone.jejuRefactoring.domain.spot.dto.response.WishListsWithPictureTagsResponseDto;
import com.capstone.jejuRefactoring.domain.spot.repository.PictureTagRepository;
import com.capstone.jejuRefactoring.domain.spot.repository.SpotRepository;
import com.capstone.jejuRefactoring.domain.wishList.service.dto.response.WishListResponseDto;
import com.capstone.jejuRefactoring.domain.wishList.service.dto.response.WishListSpotIdsResponseDto;
import com.capstone.jejuRefactoring.domain.wishList.service.dto.response.WishListsResponseDto;
import com.capstone.jejuRefactoring.infrastructure.spot.dto.PictureTagUrlDto;
import com.capstone.jejuRefactoring.domain.spot.dto.response.SpotWithCategoryScoreAndPictureTagUrlDto;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SpotService {

	private final SpotRepository spotRepository;
	private final PictureTagRepository pictureTagRepository;

	public SpotResponse getBySpotId(final Long spotId) {
		Spot spot = spotRepository.findById(spotId).orElseThrow(() -> new SpotNotFoundException());
		return SpotResponse.from(spot);
	}

	public SpotForRouteRecommendResponse getSpotWithPictureTagPerLocations(List<Location> locations,
		Category category) {
		Set<Long> spotIdSet = new HashSet<>();
		Map<Location, List<SpotWithCategoryScoreAndPictureTagUrlDto>> spotsByLocationMap = spotRepository.findWithCategoryScoreByLocation(
				locations, category)
			.stream()
			.map(SpotWithCategoryScoreAndPictureTagUrlDto::from)
			.collect(Collectors.groupingBy(s -> s.getLocation()));
		List<SpotForRouteRecommendDto> spotForRouteRecommendDtos = getSpotForRouteRecommendDtos(spotIdSet,
			spotsByLocationMap);
		setPictureUrlDto(spotIdSet, spotForRouteRecommendDtos);
		return SpotForRouteRecommendResponse.from(category, spotForRouteRecommendDtos);
	}

	private void setPictureUrlDto(Set<Long> spotIdSet, List<SpotForRouteRecommendDto> spotForRouteRecommendDtos) {
		Map<Long, List<PictureTagUrlDto>> pictureTagUrlDtoBySpotIdMap = getPictureTagUrlDtoBySpotIdMap(spotIdSet);
		for (SpotForRouteRecommendDto spotForRouteRecommendDto : spotForRouteRecommendDtos) {
			spotForRouteRecommendDto.getSpotWithCategoryScoreAndPictureTagUrlDtos()
				.stream()
				.forEach(s -> s.setPictureUrl(pictureTagUrlDtoBySpotIdMap.get(s.getId()).get(0)));
		}
	}

	private Map<Long, List<PictureTagUrlDto>> getPictureTagUrlDtoBySpotIdMap(Set<Long> spotIdSet) {
		List<Long> spotIds = spotIdSet.stream().toList();
		Map<Long, List<PictureTagUrlDto>> pictureTagUrlDtoBySpotIdMap = pictureTagRepository.findNPictureTagForSpotIds(
				spotIds, spotIds.size())
			.stream()
			.collect(Collectors.groupingBy(pictureTagUrlDto -> pictureTagUrlDto.getSpotId()));
		return pictureTagUrlDtoBySpotIdMap;
	}

	private List<SpotForRouteRecommendDto> getSpotForRouteRecommendDtos(Set<Long> spotIdSet,
		Map<Location, List<SpotWithCategoryScoreAndPictureTagUrlDto>> spotsByLocationMap) {
		List<SpotForRouteRecommendDto> spotForRouteRecommendDtos = new ArrayList<>();
		for (Location location : spotsByLocationMap.keySet()) {
			spotsByLocationMap.get(location).stream().sorted().limit(10).forEach(s -> spotIdSet.add(s.getId()));
			SpotForRouteRecommendDto.from(location, spotsByLocationMap.get(location));
		}
		return spotForRouteRecommendDtos;
	}

	public List<Long> getAllSpotIds() {
		return spotRepository.findAllSpotIds();
	}

	public List<Long> getBySpotLocations(List<Location> locations) {
		return spotRepository.findSpotIdsByLocations(locations);
	}

	public SpotPageWithPictureTagsResponse getSpotWithPictureTagLimit3(SpotIdsWithPageInfoDto spotIdsWithPageInfoDto,
		List<Location> locations) {
		List<Long> spotIds = spotIdsWithPageInfoDto.getSpotIds();
		List<Spot> spots = spotRepository.findByLocationsAndSpotIds(spotIds, locations);
		Map<Long, List<Spot>> spotContentsBySpotIdMap = getBySpotIdMap(spots);
		Map<Long, List<PictureTagResponse>> pictureTagResponseBySpotIdMap = getPictureTagResponseBySpotIdMap(spotIds);
		return SpotPageWithPictureTagsResponse.of(spotIdsWithPageInfoDto.isHasNext(),
			getSpotWithPictureTagsDtosOrderByRank(
				spotIds, spotContentsBySpotIdMap, pictureTagResponseBySpotIdMap));
	}

	private List<SpotWithPictureTagsDto> getSpotWithPictureTagsDtosOrderByRank(List<Long> spotIds,
		Map<Long, List<Spot>> spotContentsBySpotIdMap, Map<Long, List<PictureTagResponse>> pictureResponseBySpotIdMap) {
		List<SpotWithPictureTagsDto> spotWithPictureTagsDtos = new ArrayList<>();
		for (Long spotId : spotIds) {
			addSpotWithPictureTagsDtoInSpotWithPictureTagsDtos(spotContentsBySpotIdMap, pictureResponseBySpotIdMap,
				spotWithPictureTagsDtos, spotId);
		}
		return spotWithPictureTagsDtos;
	}

	private void addSpotWithPictureTagsDtoInSpotWithPictureTagsDtos(Map<Long, List<Spot>> spotContentsBySpotIdMap,
		Map<Long, List<PictureTagResponse>> pictureResponseBySpotIdMap,
		List<SpotWithPictureTagsDto> spotWithPictureTagsDtos, Long spotId) {
		List<PictureTagResponse> pictureTagResponse = pictureResponseBySpotIdMap.get(spotId)
			.stream()
			.limit(3)
			.collect(Collectors.toList());
		spotWithPictureTagsDtos.add(
			SpotWithPictureTagsDto.of(spotContentsBySpotIdMap.get(spotId).get(0), pictureTagResponse));
	}

	private Map<Long, List<Spot>> getBySpotIdMap(List<Spot> spotSlice) {
		Map<Long, List<Spot>> spotContentsBySpotIdMap = spotSlice
			.stream()
			.collect(Collectors.groupingBy(spot -> spot.getId()));
		return spotContentsBySpotIdMap;
	}

	private Map<Long, List<PictureTagResponse>> getPictureTagResponseBySpotIdMap(List<Long> spotIds) {
		Map<Long, List<PictureTagResponse>> pictureTagResponseBySpotIdMap = pictureTagRepository.findBySpotIds(spotIds)
			.stream()
			.map(pictureTag -> PictureTagResponse.of(pictureTag, pictureTag.getSpot().getId()))
			.collect(Collectors.groupingBy(pictureTagResponse -> pictureTagResponse.getSpotId()));
		return pictureTagResponseBySpotIdMap;
	}

	public SpotsForRouteDto getSpotInfoBySpotIdsForRoute(WishListSpotIdsResponseDto wishListSpotIdsResponseDto) {
		//1. spotId 리스트들 기반으로 관광지 사진 1개, 관광지 이름, location, address 를 가져온다
		List<Long> spotIds = wishListSpotIdsResponseDto.getSpotIds();
		List<Spot> spots = spotRepository.findBySpotIdsWithFetchJoin(spotIds);
		return SpotsForRouteDto.of(wishListSpotIdsResponseDto.getWishListId(), spots.stream()
			.map(spot -> SpotWithPictureTagDto.of(spot, PictureTagDto.from(spot.getPictureTags().get(0))))
			.collect(Collectors.toList()));
	}

	public WishListsWithPictureTagsResponseDto getPictureTagsForWishLists(WishListsResponseDto wishListsResponseDto) {
		// 위시리시트 페이지에서 위시리스트마다 사진 3개 가져오기
		// WishListResponseDto 안에 있는 사진들 가져오고, wishlist id, name 과 함계 DTO 에 감싸서 반환해주기
		List<SpotPictureTagForWishListResponseDto> spotPictureTagForWishListResponseDtos = new ArrayList<>();
		for (WishListResponseDto wishListResponseDto : wishListsResponseDto.getWishListResponseDtos()) {
			List<Long> spotIds = wishListResponseDto.getSpotIds();
			spotPictureTagForWishListResponseDtos.add(SpotPictureTagForWishListResponseDto.of(wishListResponseDto,
				pictureTagRepository.findNPictureTagForSpotIds(spotIds, 3)));
		}
		return WishListsWithPictureTagsResponseDto.of(wishListsResponseDto.getMemberId(),
			spotPictureTagForWishListResponseDtos);
	}

	public SpotPageWithPictureTagsResponse getSpotsBySpotName(String spotName, Long lastSpotId, Pageable pageable) {
		Slice<SpotPageResponse> bySpotName = spotRepository.findPageBySpotName(spotName, lastSpotId, pageable);
		List<SpotPageResponse> pageBySpotName = bySpotName.getContent();
		List<Long> spotIds = pageBySpotName.stream().map(s -> s.getId()).collect(Collectors.toList());
		Map<Long, List<PictureTagResponse>> pictureTagResponseBySpotIdMap = pictureTagRepository.findBySpotIds(spotIds)
			.stream()
			.map(pictureTag -> PictureTagResponse.of(pictureTag, pictureTag.getSpot().getId()))
			.collect(Collectors.groupingBy(p -> p.getSpotId()));
		return getSpotPageWithPictureTagsResponse(bySpotName, pageBySpotName, pictureTagResponseBySpotIdMap);
	}

	private SpotPageWithPictureTagsResponse getSpotPageWithPictureTagsResponse(Slice<SpotPageResponse> bySpotName,
		List<SpotPageResponse> pageBySpotName, Map<Long, List<PictureTagResponse>> pictureTagResponseBySpotIdMap) {
		List<SpotWithPictureTagsDto> spotWithPictureTagsDtos = new ArrayList<>();
		for (SpotPageResponse spotPageResponse : pageBySpotName) {
			SpotWithPictureTagsDto spotWithPictureTagsDto = SpotWithPictureTagsDto.of(spotPageResponse);
			spotWithPictureTagsDto.setPictureTagResponses(pictureTagResponseBySpotIdMap.get(spotPageResponse.getId()));
			spotWithPictureTagsDtos.add(spotWithPictureTagsDto);
		}
		return SpotPageWithPictureTagsResponse.of(bySpotName.hasNext(), spotWithPictureTagsDtos);
	}
}
