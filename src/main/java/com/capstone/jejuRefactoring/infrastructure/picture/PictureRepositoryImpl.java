package com.capstone.jejuRefactoring.infrastructure.picture;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

import com.capstone.jejuRefactoring.domain.picture.Picture;
import com.capstone.jejuRefactoring.domain.picture.repsoitory.PictureRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PictureRepositoryImpl implements PictureRepository {

	private final PictureQuerydslRepository pictureQuerydslRepository;

	@Override
	public Slice<Picture> findPageBySpotId(Long spotId, Pageable pageable) {
		return pictureQuerydslRepository.findPageBySpotId(spotId, pageable);
	}

}
