package com.capstone.jejuRefactoring.support.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;

import org.springframework.data.domain.Pageable;

import com.capstone.jejuRefactoring.common.support.RepositorySupport;
import com.capstone.jejuRefactoring.domain.picture.Picture;
import com.capstone.jejuRefactoring.domain.picture.dto.PicturePageResponse;
import com.capstone.jejuRefactoring.domain.review.Review;
import com.capstone.jejuRefactoring.domain.spot.PictureTag;
import com.capstone.jejuRefactoring.domain.spot.Spot;

public class PictureGivenHelper {
	public static Picture givenPicture(Long spotId) {
		return Picture.builder()
			.spot(Spot.builder().id(spotId).build())
			.url("http:~~~")
			.build();
	}

	public static PicturePageResponse givenPicturePageResponse(Pageable pageable, int count) {
		List<Picture> pictures = new ArrayList<>();
		LongStream.range(1, 1 + count).forEach(i -> pictures.add(givenPicture(i)));
		return PicturePageResponse.of(RepositorySupport.toSlice(pictures, pageable), 1l);
	}

	public static Picture givenPictureWithUrl(Long spotId, String url) {
		return Picture.builder()
			.url(url)
			.spot(Spot.builder().id(spotId).build())
			.build();
	}

}
