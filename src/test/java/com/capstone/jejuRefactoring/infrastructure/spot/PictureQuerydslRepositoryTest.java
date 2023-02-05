package com.capstone.jejuRefactoring.infrastructure.spot;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.capstone.jejuRefactoring.domain.spot.Picture;
import com.capstone.jejuRefactoring.domain.spot.Spot;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@Transactional
class PictureQuerydslRepositoryTest {

	@Autowired
	SpotJpaRepository spotJpaRepository;

	@Autowired
	PictureQuerydslRepository pictureQuerydslRepository;

	@PersistenceContext
	EntityManager em;

	@Test
	public void 쿼리_테스트() throws Exception{
	    //given
		Spot spot = Spot.builder()
			.address("ss")
			.description("ss")
			.build();



		Picture picture1 = Picture.builder()
			.spot(spot)
			.url("url")
			.build();

		Picture picture2 = Picture.builder()
			.spot(spot)
			.url("url")
			.build();

		Picture picture3 = Picture.builder()
			.spot(spot)
			.url("url")
			.build();


		Spot spot2 = Spot.builder()
			.address("ss")
			.description("ss")
			.build();



		Picture picture4 = Picture.builder()
			.spot(spot2)
			.url("url")
			.build();

		Picture picture5 = Picture.builder()
			.spot(spot2)
			.url("url")
			.build();

		Picture picture6 = Picture.builder()
			.spot(spot2)
			.url("url")
			.build();



		em.persist(spot);
		em.persist(picture1);
		em.persist(picture2);
		em.persist(picture3);
		em.flush();
		em.clear();

	    //when
		List<Long> spotIds = spotJpaRepository.findAll().stream().map(s -> s.getId()).collect(Collectors.toList());
		// List<PictureUrlDto> nPictureForEachSpotId = pictureQuerydslRepository.getNPictureForEachSpotId(spotIds);
		// log.info("nPictureForEachSpotId = {}", nPictureForEachSpotId);


		//then
	}

}