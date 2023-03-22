package com.capstone.jejuRefactoring.infrastructure.spot;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.capstone.jejuRefactoring.domain.spot.PictureTag;
import com.capstone.jejuRefactoring.domain.spot.repository.PictureTagRepository;
import com.capstone.jejuRefactoring.infrastructure.spot.dto.PictureTagUrlDto;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Transactional()
@Repository
@RequiredArgsConstructor
public class PictureTagRepositoryImpl implements PictureTagRepository {

	private final PictureTagJpaRepository pictureTagJpaRepository;

	private final PictureTagQuerydslRepository pictureTagQuerydslRepository;

	@Override
	public List<PictureTag> findBySpotIds(List<Long> spotIds) {
		return pictureTagJpaRepository.findBySpotIds(spotIds);
	}

	@Override
	public List<PictureTagUrlDto> findNPictureTagForSpotIds(List<Long> spoIds, Integer limit) {
		return pictureTagQuerydslRepository.findNPictureTagForSpotIds(spoIds, limit);
	}
}


