package com.capstone.jejuRefactoring.unit.infrastructure.spot;

import static com.capstone.jejuRefactoring.support.helper.SpotGivenHelper.*;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.capstone.jejuRefactoring.domain.spot.PictureTag;
import com.capstone.jejuRefactoring.domain.spot.Spot;
import com.capstone.jejuRefactoring.infrastructure.spot.PictureTagJpaRepository;
import com.capstone.jejuRefactoring.infrastructure.spot.PictureTagRepositoryImpl;
import com.capstone.jejuRefactoring.infrastructure.spot.SpotJpaRepository;
import com.capstone.jejuRefactoring.infrastructure.spot.dto.PictureTagUrlDto;
import com.capstone.jejuRefactoring.support.QuerydslRepositoryTest;

public class PictureTagRepositoryImplTest extends QuerydslRepositoryTest {

	@Autowired
	PictureTagJpaRepository pictureTagJpaRepository;
	@Autowired
	PictureTagRepositoryImpl pictureTagRepository;
	@Autowired
	SpotJpaRepository spotJpaRepository;

	@Test
	public void spotId들로_pictureTag_찾기() throws Exception {
		//given
		List<Spot> spots = createSpotListWithPictureTags();

		//when
		List<Long> spotIds = spots.stream().map(s -> s.getId()).toList();
		List<PictureTag> bySpotIds = pictureTagRepository.findBySpotIds(spotIds);

		//then
		Assertions.assertThat(bySpotIds.size()).isEqualTo(6);
	}

	private List<Spot> createSpotListWithPictureTags() {
		List<Spot> spots = spotJpaRepository.saveAll(
			List.of(givenSpotWithName("관광지1"), givenSpotWithName("관광지2"), givenSpotWithName("관광지3"))
		);
		pictureTagJpaRepository.saveAll(
			List.of(givenPictureTagWithId(spots.get(0).getId()), givenPictureTagWithId(spots.get(0).getId()),
				givenPictureTagWithId(spots.get(1).getId()), givenPictureTagWithId(spots.get(1).getId()),
				givenPictureTagWithId(spots.get(2).getId()), givenPictureTagWithId(spots.get(2).getId())
			)
		);
		return spots;
	}

	@Test
	public void 각각의_spotId들로_하나의_pictureTag_찾기() throws Exception{
	    //given
		List<Spot> spots = createSpotListWithPictureTags();

	    //when
		List<Long> spotIds = spots.stream().map(s -> s.getId()).toList();
		List<PictureTagUrlDto> pictureTagUrlDtos = pictureTagRepository.findNPictureTagForSpotIds(spotIds,
			spotIds.size());

		//then
		Assertions.assertThat(pictureTagUrlDtos.size()).isEqualTo(3);
	}

}
