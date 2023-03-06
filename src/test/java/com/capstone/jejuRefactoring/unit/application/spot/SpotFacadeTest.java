package com.capstone.jejuRefactoring.unit.application.spot;

import static com.capstone.jejuRefactoring.support.helper.PreferenceGivenHelper.*;
import static com.capstone.jejuRefactoring.support.helper.SpotGivenHelper.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.capstone.jejuRefactoring.application.spot.SpotFacade;
import com.capstone.jejuRefactoring.domain.preference.Score;
import com.capstone.jejuRefactoring.domain.preference.service.PreferenceService;
import com.capstone.jejuRefactoring.domain.spot.dto.response.SpotPageWithPictureTagsResponse;
import com.capstone.jejuRefactoring.domain.spot.dto.response.SpotResponse;
import com.capstone.jejuRefactoring.domain.spot.service.SpotService;

@ExtendWith(MockitoExtension.class)
public class SpotFacadeTest {

	@InjectMocks
	SpotFacade spotFacade;
	@Mock
	SpotService spotService;
	@Mock
	PreferenceService preferenceService;

	@Test
	public void 관광지_정보_가져오기() throws Exception{
	    //given
		given(spotService.getBySpotId(any())).willReturn(givenSpotResponse(givenScore(1l)));
		given(preferenceService.getScoreBySpotId(any(), any()))
			.willReturn(givenSpotResponse(givenScore(1l)));

	    //when
		SpotResponse result = spotFacade.getBySpotId(1l);

		//then
		assertThat(result.getId()).isEqualTo(1l);
	}

	@Test
	public void 관광지_검색_내역_가져오기() throws Exception{
	    //given
		given(spotService.getSpotsBySpotName(any(), any(), any()))
			.willReturn(givenSpotPageWithPictureTagsResponse(true,3));

		//when
		SpotPageWithPictureTagsResponse result = spotFacade.getSpotBySpotName(any(), any(), any());

	    //then
		assertThat(result.isHasNext()).isTrue();
		assertThat(result.getSpotWithPictureTagsDtos().size()).isEqualTo(3);
	}

}


