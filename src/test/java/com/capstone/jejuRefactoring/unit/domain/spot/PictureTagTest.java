package com.capstone.jejuRefactoring.unit.domain.spot;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.capstone.jejuRefactoring.domain.spot.PictureTag;
import com.capstone.jejuRefactoring.domain.spot.Spot;
import com.capstone.jejuRefactoring.infrastructure.spot.PictureTagJpaRepository;
import com.capstone.jejuRefactoring.infrastructure.spot.SpotJpaRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@SpringBootTest
@Transactional
class PictureTagTest {

	@Autowired
	SpotJpaRepository spotJpaRepository;

	@Autowired
	PictureTagJpaRepository pictureTagJpaRepository;

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

		PictureTag pictureTag = PictureTag.builder()
			.spot(spot)
			.url("url")
			.build();

		PictureTag pictureTag1 = PictureTag.builder()
			.spot(spot1)
			.url("url")
			.build();

		em.persist(spot);
		em.persist(spot1);
		em.persist(pictureTag);
		em.persist(pictureTag1);
		// em.persist(pictureTag);
		em.flush();
		em.clear();

		List<Long> longs = new ArrayList<>();
		longs.add(1l);
		longs.add(2l);

		System.out.println("=========");

		pictureTagJpaRepository.findBySpotIds(longs);

		System.out.println("=========");

	}
}