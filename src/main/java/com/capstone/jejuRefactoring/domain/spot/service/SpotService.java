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
import com.capstone.jejuRefactoring.domain.spot.Category;
import com.capstone.jejuRefactoring.domain.spot.Location;
import com.capstone.jejuRefactoring.domain.spot.Spot;
import com.capstone.jejuRefactoring.domain.spot.dto.response.PictureTagResponse;
import com.capstone.jejuRefactoring.domain.spot.dto.response.SpotForRouteDto;
import com.capstone.jejuRefactoring.domain.spot.dto.response.SpotForRouteRecommendDto;
import com.capstone.jejuRefactoring.domain.spot.dto.response.SpotForRouteRecommendResponse;
import com.capstone.jejuRefactoring.domain.spot.dto.response.SpotPageWithPictureTagsResponse;
import com.capstone.jejuRefactoring.domain.spot.dto.response.SpotPictureTagForWishListResponseDto;
import com.capstone.jejuRefactoring.domain.spot.dto.response.SpotResponse;
import com.capstone.jejuRefactoring.domain.spot.dto.response.SpotWithPictureTagsDto;
import com.capstone.jejuRefactoring.domain.spot.dto.response.SpotsForRouteDto;
import com.capstone.jejuRefactoring.domain.spot.dto.response.WishListsWithPictureTagsResponseDto;
import com.capstone.jejuRefactoring.domain.spot.repository.PictureTagRepository;
import com.capstone.jejuRefactoring.domain.spot.repository.SpotRepository;
import com.capstone.jejuRefactoring.domain.wishList.service.dto.response.WishListResponseDto;
import com.capstone.jejuRefactoring.domain.wishList.service.dto.response.WishListSpotIdsResponseDto;
import com.capstone.jejuRefactoring.domain.wishList.service.dto.response.WishListsResponseDto;
import com.capstone.jejuRefactoring.infrastructure.spot.PictureTagUrlDto;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class SpotService {

	private final SpotRepository spotRepository;
	private final PictureTagRepository pictureTagRepository;



	public SpotResponse getBySpotId(final Long spotId) {
		Spot spot = spotRepository.findById(spotId).orElseThrow(() -> new SpotNotFoundException());
		return SpotResponse.from(spot);
	}

	public SpotForRouteRecommendResponse getSpotWithPictureTagPerLocations(List<Location> locations, Category category) {
		//필요한 spotId 들을 구하고 한번에 pictureTag 쿼리를 날린다
		Set<Long> spotIdSet = new HashSet<>();
		//category 기준으로 spot '들'을 정렬하고, 그중에 지역별로 10개씩 가져온다
		List<SpotForRouteRecommendDto> spotForRouteRecommendDtos = getSpotForRouteRecommendDtos(locations, category, spotIdSet);
		Map<Long, List<PictureTagUrlDto>> pictureTagUrlDtoBySpotIdMap = findPictureTagsGroupBySpotId(spotIdSet);
		setPictureTagUrlDtoInSpotForRouteRecommendDtos(spotForRouteRecommendDtos, pictureTagUrlDtoBySpotIdMap);
		return SpotForRouteRecommendResponse.from(category, spotForRouteRecommendDtos);

	}

	private List<SpotForRouteRecommendDto> getSpotForRouteRecommendDtos(List<Location> locations, Category category,
		Set<Long> spotIdSet) {
		List<SpotForRouteRecommendDto> spotForRouteRecommendDtos = new ArrayList<>();
		for (Location location : locations) {
			//특정 location 에 category 기준으로 정렬된한것을
			List<Spot> spots = spotRepository.findByLocationAndCategory(location, category);
			spots.stream().forEach(spot -> spotIdSet.add(spot.getId()));
			addSpotForRouteRecommendDto(spotForRouteRecommendDtos, location, spots);
		}
		return spotForRouteRecommendDtos;
	}

	private Map<Long, List<PictureTagUrlDto>> findPictureTagsGroupBySpotId(Set<Long> spotIdSet) {
		List<Long> spotIds = spotIdSet.stream().toList();
		Map<Long, List<PictureTagUrlDto>> pictureTagUrlDtoBySpotIdMap = pictureTagRepository.findNPictureTagForSpotIds(spotIds,
				spotIds.size())
			.stream()
			.collect(Collectors.groupingBy(pictureTagUrlDto -> pictureTagUrlDto.getSpotId()));
		return pictureTagUrlDtoBySpotIdMap;
	}

	private void setPictureTagUrlDtoInSpotForRouteRecommendDtos(List<SpotForRouteRecommendDto> spotForRouteRecommendDtos,
		Map<Long, List<PictureTagUrlDto>> pictureTagUrlDtoBySpotIdMap) {
		for (SpotForRouteRecommendDto spotForRouteRecommendDto : spotForRouteRecommendDtos) {
			spotForRouteRecommendDto.getSpotForRouteDtos()
				.stream()
				.forEach(spotForRouteDto -> spotForRouteDto.setPictureTagUrlDto(
					pictureTagUrlDtoBySpotIdMap.get(spotForRouteDto.getId()).get(0)));

		}
	}

	private void addSpotForRouteRecommendDto(List<SpotForRouteRecommendDto> spotForRouteRecommendDtos, Location location,
		List<Spot> spots) {
		List<SpotForRouteDto> spotForRouteDtos = spots.stream()
			.map(spot -> SpotForRouteDto.from(spot))
			.collect(Collectors.toList());
		spotForRouteRecommendDtos.add(SpotForRouteRecommendDto.from(location, spotForRouteDtos));
	}

	public List<Long> getBySpotIds() {
		return spotRepository.findAllSpotIds();
	}

	public List<Long> getBySpotLocations(List<Location> locations) {
		return spotRepository.findBySpotLocations(locations);
	}

	public SpotPageWithPictureTagsResponse getSpotWithPictureTagLimit3(List<Long> spotIds, List<Location> locations,
		Pageable pageable) {
		Slice<Spot> spotSlice = spotRepository.findPageBySpotIds(spotIds, locations, pageable);
		Map<Long, List<Spot>> spotContentsBySpotIdMap = getBySpotIdMap(spotSlice);
		Map<Long, List<PictureTagResponse>> pictureTagResponseBySpotIdMap = getPictureTagResponseBySpotIdMap(spotIds);
		return SpotPageWithPictureTagsResponse.of(spotSlice.hasNext(), getSpotWithPictureTagsDtos(
			spotIds, spotContentsBySpotIdMap, pictureTagResponseBySpotIdMap));
	}

	private List<SpotWithPictureTagsDto> getSpotWithPictureTagsDtos(List<Long> spotIds,
		Map<Long, List<Spot>> spotContentsBySpotIdMap, Map<Long, List<PictureTagResponse>> pictureResponseBySpotIdMap) {
		List<SpotWithPictureTagsDto> spotWithPictureTagsDtos = new ArrayList<>();
		for (Long spotId : spotIds) {
			List<PictureTagResponse> pictureTagResponse = pictureResponseBySpotIdMap.get(spotId)
				.stream()
				.limit(3)
				.collect(Collectors.toList());
			spotWithPictureTagsDtos.add(
				SpotWithPictureTagsDto.of(spotContentsBySpotIdMap.get(spotId).get(0), pictureTagResponse));
		}
		return spotWithPictureTagsDtos;
	}

	private Map<Long, List<Spot>> getBySpotIdMap(Slice<Spot> spotSlice) {
		Map<Long, List<Spot>> spotContentsBySpotIdMap = spotSlice.getContent()
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
		List<Spot> spots = spotRepository.findBySpotIds(spotIds);
		Map<Long, List<PictureTagUrlDto>> pictureTagUrlDtoBySpotIdMap = pictureTagRepository.findNPictureTagForSpotIds(spotIds,
				spotIds.size())
			.stream()
			.collect(Collectors.groupingBy(p -> p.getSpotId()));
		return SpotsForRouteDto.of(wishListSpotIdsResponseDto.getWishListId(), spots.stream()
			.map(spot -> SpotForRouteDto.of(spot, pictureTagUrlDtoBySpotIdMap.get(spot.getId()).get(0)))
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

}
