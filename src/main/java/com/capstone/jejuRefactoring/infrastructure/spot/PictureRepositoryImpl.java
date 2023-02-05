package com.capstone.jejuRefactoring.infrastructure.spot;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

import com.capstone.jejuRefactoring.domain.spot.Picture;
import com.capstone.jejuRefactoring.domain.spot.repository.PictureRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PictureRepositoryImpl implements PictureRepository {

	private final PictureJpaRepository pictureJpaRepository;

	private final PictureQuerydslRepository pictureQuerydslRepository;

	@Override
	public Slice<Picture> findPageBySpotId(Long spotId, Pageable pageable) {
		return pictureQuerydslRepository.findPageBySpotId(spotId, pageable);
	}

	@Override
	public List<Picture> findBySpotIds(List<Long> spotIds) {
		return pictureJpaRepository.findBySpotIds(spotIds);
	}

	@Override
	public List<PictureUrlDto> findNPictureForSpotIds(List<Long> spoIds, Integer limit) {
		return pictureQuerydslRepository.findNPictureForSpotIds(spoIds, limit);
	}
}


