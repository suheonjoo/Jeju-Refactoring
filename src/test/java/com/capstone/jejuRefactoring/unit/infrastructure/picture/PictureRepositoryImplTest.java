package com.capstone.jejuRefactoring.unit.infrastructure.picture;

import static com.capstone.jejuRefactoring.support.helper.PictureGivenHelper.*;
import static com.capstone.jejuRefactoring.support.helper.SpotGivenHelper.*;
import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

import com.capstone.jejuRefactoring.domain.picture.Picture;
import com.capstone.jejuRefactoring.domain.spot.dto.response.SpotPageResponse;
import com.capstone.jejuRefactoring.infrastructure.picture.PictureJpaRepository;
import com.capstone.jejuRefactoring.infrastructure.picture.PictureRepositoryImpl;
import com.capstone.jejuRefactoring.infrastructure.spot.SpotJpaRepository;
import com.capstone.jejuRefactoring.support.QuerydslRepositoryTest;

public class PictureRepositoryImplTest extends QuerydslRepositoryTest {

	@Autowired
	PictureRepositoryImpl pictureRepository;
	@Autowired
	PictureJpaRepository pictureJpaRepository;
	@Autowired
	SpotJpaRepository spotJpaRepository;

	@Test
	public void 페이징된_관광지사진찾기_다음페이지없음() throws Exception{
	    //given
		Long spotId = spotJpaRepository.save(givenSpot()).getId();
		pictureJpaRepository.saveAll(List.of(
				givenPicture(spotId), givenPicture(spotId), givenPicture(spotId)
			)
		);

		//when
		PageRequest pageRequest = PageRequest.of(0, 20);
		Slice<Picture> target =
			pictureRepository.findPageBySpotId(spotId, pageRequest);

		//then
		assertThat(target.getContent().size()).isEqualTo(3);
		assertThat(target.hasNext()).isFalse();
	}

	@Test
	public void 페이징된_관광지사진찾기_다음페이지있음() throws Exception{
		//given
		Long spotId = spotJpaRepository.save(givenSpot()).getId();
		pictureJpaRepository.saveAll(List.of(
				givenPicture(spotId), givenPicture(spotId), givenPicture(spotId)
			)
		);

		//when
		PageRequest pageRequest = PageRequest.of(0, 1);
		Slice<Picture> target =
			pictureRepository.findPageBySpotId(spotId, pageRequest);

		//then
		assertThat(target.getContent().size()).isEqualTo(1);
		assertThat(target.hasNext()).isTrue();
	}
}
