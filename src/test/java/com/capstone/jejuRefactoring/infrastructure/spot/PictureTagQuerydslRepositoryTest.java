package com.capstone.jejuRefactoring.infrastructure.spot;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.capstone.jejuRefactoring.domain.spot.PictureTag;
import com.capstone.jejuRefactoring.domain.spot.Spot;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@Transactional
class PictureTagQuerydslRepositoryTest {

	@Autowired
	SpotJpaRepository spotJpaRepository;

	@Autowired
	PictureTagQuerydslRepository pictureTagQuerydslRepository;

	@PersistenceContext
	EntityManager em;

	@Test
	public void 쿼리_테스트() throws Exception{
	    //given
		Spot spot = Spot.builder()
			.address("ss")
			.description("ss")
			.build();



		PictureTag pictureTag1 = PictureTag.builder()
			.spot(spot)
			.url("url")
			.build();

		PictureTag pictureTag2 = PictureTag.builder()
			.spot(spot)
			.url("url")
			.build();

		PictureTag pictureTag3 = PictureTag.builder()
			.spot(spot)
			.url("url")
			.build();


		Spot spot2 = Spot.builder()
			.address("ss")
			.description("ss")
			.build();



		PictureTag pictureTag4 = PictureTag.builder()
			.spot(spot2)
			.url("url")
			.build();

		PictureTag pictureTag5 = PictureTag.builder()
			.spot(spot2)
			.url("url")
			.build();

		PictureTag pictureTag6 = PictureTag.builder()
			.spot(spot2)
			.url("url")
			.build();



		em.persist(spot);
		em.persist(pictureTag1);
		em.persist(pictureTag2);
		em.persist(pictureTag3);
		em.flush();
		em.clear();

	    //when
		List<Long> spotIds = spotJpaRepository.findAll().stream().map(s -> s.getId()).collect(Collectors.toList());
		// List<PictureUrlDto> nPictureForEachSpotId = pictureQuerydslRepository.getNPictureForEachSpotId(spotIds);
		// log.info("nPictureForEachSpotId = {}", nPictureForEachSpotId);


		//then
	}

}