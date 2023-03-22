package com.capstone.jejuRefactoring.domain.picture.repsoitory;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import com.capstone.jejuRefactoring.domain.picture.Picture;

public interface PictureRepository {

	Slice<Picture> findPageBySpotId(Long spotId, Pageable pageable);

	Slice<Picture> findPageOpBySpotId(Long spotId, Long lastPictureId, Pageable pageable);

}
