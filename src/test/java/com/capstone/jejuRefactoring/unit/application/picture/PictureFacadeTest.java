package com.capstone.jejuRefactoring.unit.application.picture;

import static com.capstone.jejuRefactoring.support.helper.PictureGivenHelper.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import com.capstone.jejuRefactoring.application.picture.PictureFacade;
import com.capstone.jejuRefactoring.domain.picture.dto.PicturePageResponse;
import com.capstone.jejuRefactoring.domain.picture.service.PictureService;

@ExtendWith(MockitoExtension.class)
public class PictureFacadeTest {

	@InjectMocks
	PictureFacade pictureFacade;
	@Mock
	PictureService pictureService;
	@Test
	public void 대량_관광지_사진_가져오기() throws Exception{
	    //given
		PageRequest pageRequest = PageRequest.of(0, 3);
		given(pictureService.getPicturesBySpotId(1l, 1l,pageRequest))
			.willReturn(givenPicturePageResponse(pageRequest,3));

	    //when
		PicturePageResponse result = pictureFacade.getPicturesBySpotId(1l, 1l,pageRequest);

		//then
		assertThat(result.isHasNext()).isFalse();
		assertThat(result.getPictureResponse().size()).isEqualTo(3);

	}

}
