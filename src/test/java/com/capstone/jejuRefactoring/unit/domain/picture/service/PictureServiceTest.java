package com.capstone.jejuRefactoring.unit.domain.picture.service;


import static com.capstone.jejuRefactoring.support.helper.PictureGivenHelper.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

import com.capstone.jejuRefactoring.common.support.RepositorySupport;
import com.capstone.jejuRefactoring.domain.picture.Picture;
import com.capstone.jejuRefactoring.domain.picture.dto.PicturePageResponse;
import com.capstone.jejuRefactoring.domain.picture.repsoitory.PictureRepository;
import com.capstone.jejuRefactoring.domain.picture.service.PictureService;
import com.capstone.jejuRefactoring.domain.review.repository.ReviewRepository;
import com.capstone.jejuRefactoring.domain.review.service.ReviewService;

@ExtendWith(MockitoExtension.class)
public class PictureServiceTest {
	@InjectMocks
	PictureService pictureService;
	@Mock
	PictureRepository pictureRepository;

	@Test
	public void 페이징된_대량관광지사진_가져오기() throws Exception{
	    //given
		List<Picture> pictures = List.of(givenPicture(1l), givenPicture(1l), givenPicture(1l));
		PageRequest pageRequest = PageRequest.of(0, 20);
		Slice<Picture> slice = RepositorySupport.toSlice(pictures, pageRequest);
		given(pictureRepository.findPageBySpotId(any(), any())).willReturn(slice);

	    //when
		PicturePageResponse result = pictureService.getPicturesBySpotId(1l, pageRequest);

		//then
		assertThat(result.isHasNext()).isFalse();
		assertThat(result.getPictureResponse().size()).isEqualTo(3);
	}
}
