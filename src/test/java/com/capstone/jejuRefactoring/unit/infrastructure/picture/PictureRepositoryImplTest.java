package com.capstone.jejuRefactoring.unit.infrastructure.picture;

import static com.capstone.jejuRefactoring.support.helper.PictureGivenHelper.*;
import static com.capstone.jejuRefactoring.support.helper.SpotGivenHelper.*;
import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

import com.capstone.jejuRefactoring.domain.picture.Picture;
import com.capstone.jejuRefactoring.infrastructure.picture.PictureJpaRepository;
import com.capstone.jejuRefactoring.infrastructure.picture.PictureRepositoryImpl;
import com.capstone.jejuRefactoring.infrastructure.spot.SpotJpaRepository;
import com.capstone.jejuRefactoring.support.QuerydslRepositoryTest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PictureRepositoryImplTest extends QuerydslRepositoryTest {

	@Autowired
	PictureRepositoryImpl pictureRepository;
	@Autowired
	PictureJpaRepository pictureJpaRepository;
	@Autowired
	SpotJpaRepository spotJpaRepository;

	@PersistenceContext
	EntityManager em;

	@Test
	public void 페이징된_관광지사진찾기_다음페이지없음() throws Exception {
		//given
		Long spotId = spotJpaRepository.save(givenSpot()).getId();
		pictureJpaRepository.saveAll(List.of(
				givenPicture(spotId), givenPicture(spotId), givenPicture(spotId)
			)
		);

		//when
		PageRequest pageRequest = PageRequest.of(0, 20);
		Slice<Picture> target =
			pictureRepository.findPageOpBySpotId(spotId, null, pageRequest);

		//then
		assertThat(target.getContent().size()).isEqualTo(3);
		assertThat(target.hasNext()).isFalse();
	}



	@Disabled
	@Test
	public void 대량_데이터_성능_테스트() throws Exception {
		//given
		Long spotId = spotJpaRepository.save(givenSpot()).getId();

		List<Picture> pictureList = new ArrayList<>();
		for (int i = 0; i < 800000; i++) {
			pictureList.add(givenPictureWithUrl(spotId, i + ""));
		}
		pictureJpaRepository.saveAllAndFlush(pictureList);
		// em.createQuery("insert into Picture p ")

		em.flush();
		em.clear();

		//when
		PageRequest pageRequest = PageRequest.of(100, 10);

		long beforeTime = System.currentTimeMillis();
		pictureRepository.findPageBySpotId(spotId, pageRequest);
		long afterTime = System.currentTimeMillis();
		long secDiffTime = (afterTime - beforeTime);
		log.info("시간차이(m) 평범 : {}", secDiffTime); //92

		long beforeTime1 = System.currentTimeMillis();
		pictureRepository.findPageOpBySpotId(spotId, 999l, pageRequest);
		long afterTime1 = System.currentTimeMillis();
		long secDiffTime1 = (afterTime1 - beforeTime1);
		log.info("시간차이(m) 최적화 : {}", secDiffTime1); //136

		//then
	}

	@Test
	public void 페이징된_관광지사진찾기_다음페이지있음() throws Exception {
		//given
		Long spotId = spotJpaRepository.save(givenSpot()).getId();
		List<Picture> pictureList = pictureJpaRepository.saveAll(List.of(
				givenPicture(spotId), givenPicture(spotId), givenPicture(spotId), givenPicture(spotId),
				givenPicture(spotId), givenPicture(spotId), givenPicture(spotId), givenPicture(spotId),
			givenPicture(spotId), givenPicture(spotId)
			)
		);
		// pictureList.stream().forEach(s -> System.out.println(s.getId()));

		//when
		PageRequest pageRequest = PageRequest.of(2, 3);
		Slice<Picture> target =
			pictureRepository.findPageOpBySpotId(spotId, 3l, pageRequest);

		log.info("target.getContent() = {}", target.getContent());
		log.info("target.hasNext() = {}", target.hasNext());
		//then
		assertThat(target.getContent().size()).isEqualTo(3);
		assertThat(target.hasNext()).isTrue();
	}
}
