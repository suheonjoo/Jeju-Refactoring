package com.capstone.jejuRefactoring.domain.spot;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.capstone.jejuRefactoring.infrastructure.spot.PictureJpaRepository;
import com.capstone.jejuRefactoring.infrastructure.spot.SpotJpaRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;


@SpringBootTest
@Transactional
class PictureTest {

	@Autowired
	SpotJpaRepository spotJpaRepository;

	@Autowired
	PictureJpaRepository pictureJpaRepository;

	@PersistenceContext
	EntityManager em;

	@Test
	void 사진_테스트() {

		Spot spot = Spot.builder()
			.address("ss")
			.description("ss")
			.build();

		Spot spot1 = Spot.builder()
			.address("ss")
			.description("ss")
			.build();


		Picture picture = Picture.builder()
			.spot(spot)
			.url("url")
			.build();

		Picture picture1 = Picture.builder()
			.spot(spot1)
			.url("url")
			.build();



		em.persist(spot);
		em.persist(spot1);
		em.persist(picture);
		em.persist(picture1);
		// em.persist(picture);
		em.flush();
		em.clear();

		List<Long> longs = new ArrayList<>();
		longs.add(1l);
		longs.add(2l);

		System.out.println("=========");

		pictureJpaRepository.findBySpotIds(longs);

		System.out.println("=========");

	}
}