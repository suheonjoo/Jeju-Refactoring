package com.capstone.jejuRefactoring.unit.infrastructure.spot;

import static com.capstone.jejuRefactoring.support.helper.PreferenceGivenHelper.*;
import static com.capstone.jejuRefactoring.support.helper.SpotGivenHelper.*;
import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

import com.capstone.jejuRefactoring.domain.preference.Score;
import com.capstone.jejuRefactoring.domain.spot.Category;
import com.capstone.jejuRefactoring.domain.spot.Location;
import com.capstone.jejuRefactoring.domain.spot.PictureTag;
import com.capstone.jejuRefactoring.domain.spot.Spot;
import com.capstone.jejuRefactoring.domain.spot.dto.response.SpotPageResponse;
import com.capstone.jejuRefactoring.infrastructure.preference.ScoreJpaRepository;
import com.capstone.jejuRefactoring.infrastructure.spot.PictureTagJpaRepository;
import com.capstone.jejuRefactoring.infrastructure.spot.SpotJpaRepository;
import com.capstone.jejuRefactoring.infrastructure.spot.SpotQuerydslRepository;
import com.capstone.jejuRefactoring.infrastructure.spot.SpotRepository;
import com.capstone.jejuRefactoring.infrastructure.spot.dto.SpotWithCategoryScoreDto;
import com.capstone.jejuRefactoring.support.QuerydslRepositoryTest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SpotRepositoryImplTest extends QuerydslRepositoryTest {

	@Autowired
	SpotRepository spotRepository;
	@Autowired
	PictureTagJpaRepository pictureTagJpaRepository;
	@Autowired
	SpotJpaRepository spotJpaRepository;
	@Autowired
	ScoreJpaRepository scoreJpaRepository;
	@Autowired
	SpotQuerydslRepository spotQuerydslRepository;

	@PersistenceContext
	EntityManager em;

	@Test
	public void spotId로_spot_찾기() throws Exception {

		//given
		Spot spot = spotJpaRepository.save(givenSpot());

		//when
		Optional<Spot> targetSpot = spotRepository.findById(spot.getId());

		//then
		Assertions.assertThat(spot).isEqualTo(targetSpot.get());
	}

	@Test
	public void spotId로_spot을_picturetag들과_fetchjoin하기() throws Exception {
		//given
		List<Spot> spots = createSpotListWithPictureTags();

		em.flush();
		em.clear();

		//when
		List<Long> spotIds = spots.stream().map(s -> s.getId()).collect(Collectors.toList());
		System.out.println("===============");
		List<Spot> targetSpots = spotRepository.findBySpotIdsWithFetchJoin(spotIds);
		System.out.println("===============");

		log.info("targetSpots.get(0).getPictureTags() = {}", targetSpots.get(0).getPictureTags());


		List<Spot> spotsd = spotRepository.findBySpotIdsWithFetchJoin(List.of(spots.get(0).getId()));
		log.info("spots = {}",spotsd.get(0));
		List<Spot> spotJpaRepositoryAllById = spotJpaRepository.findAllById(List.of(spots.get(0).getId()));
		log.info("spotJpaRepositoryAllById = {}",spotJpaRepositoryAllById);
		List<PictureTag> bySpotIds = pictureTagJpaRepository.findBySpotIds(List.of(spots.get(0).getId()));
		log.info("bySpotIds = {}", bySpotIds);

		//then
		Assertions.assertThat(targetSpots.size()).isEqualTo(targetSpots.size());
		Assertions.assertThat(targetSpots.get(0).getPictureTags().size()).isEqualTo(2);
	}


	private List<Spot> createSpotListWithPictureTags() {
		List<Spot> spots = spotJpaRepository.saveAll(
			List.of(givenSpotWithName("관광지1"), givenSpotWithName("관광지2"), givenSpotWithName("관광지3"))
		);
		pictureTagJpaRepository.saveAll(
			List.of(givenPictureTagWithUrl(spots.get(0).getId(), "urls1"),
				givenPictureTagWithUrl(spots.get(0).getId(), "urls2"),
				givenPictureTagWithUrl(spots.get(1).getId(), "urls3"),
				givenPictureTagWithUrl(spots.get(1).getId(), "urls4"),
				givenPictureTagWithUrl(spots.get(2).getId(), "urls5"),
				givenPictureTagWithUrl(spots.get(2).getId(), "urls6")
			)
		);
		return spots;
	}

	@Test
	public void spoId들로_spot찾기() throws Exception {
		//given
		List<Spot> spots = spotJpaRepository.saveAll(
			List.of(givenSpotWithName("관광지1"), givenSpotWithName("관광지2"), givenSpotWithName("관광지3"))
		);

		//when
		List<Long> spotIds = spots.stream().map(s -> s.getId()).toList();
		List<Long> allSpotIds = spotRepository.findAllSpotIds();

		//then
		Assertions.assertThat(spotIds).isEqualTo(allSpotIds);
	}

	@Test
	public void spotId들과_location으로_spot_찾기() throws Exception {
		//given
		List<Spot> spots = createSpotByLocation();

		//when
		List<Long> spotIds = spots.stream().map(s -> s.getId()).toList();
		List<Spot> target = spotRepository.findByLocationsAndSpotIds(spotIds,
			List.of(Location.Aewol_eup, Location.Daejeong_eup, Location.Jeju_si));

		//then
		Assertions.assertThat(target.size()).isEqualTo(3);
	}

	private List<Spot> createSpotByLocation() {
		return spotJpaRepository.saveAll(
			List.of(givenSpotWithLocation(Location.Aewol_eup), givenSpotWithLocation(Location.Daejeong_eup),
				givenSpotWithLocation(Location.Jeju_si),
				givenSpotWithLocation(Location.Gujwa_eup), givenSpotWithLocation(Location.Gujwa_eup),
				givenSpotWithLocation(Location.Gujwa_eup)
			)
		);
	}

	@Test
	public void 관광지_이름_검색내역_다음페이지_있음() throws Exception {
		//given
		List<Spot> spots = spotJpaRepository.saveAll(
			List.of(givenSpotWithName("관광지1"), givenSpotWithName("관광지2"), givenSpotWithName("관광지3"))
		);

		//when
		PageRequest pageRequest = PageRequest.of(0, 1);
		Slice<SpotPageResponse> spotPageResponses = spotRepository.findPageBySpotName("관광", 0l,
			pageRequest);

		//then
		assertThat(spotPageResponses.getContent().size()).isEqualTo(1);
		assertThat(spotPageResponses.hasNext()).isTrue();
	}

	@Disabled
	@Test
	public void 관광지_이름_검색내역_성능_튜닝_테스트() throws Exception {

		//given
		List<Spot> spotList = new ArrayList<>();
		for (int i = 0; i < 350; i++) {
			spotList.add(givenSpotWithName("관광지" + i));
		}
		spotJpaRepository.saveAllAndFlush(spotList);
		em.flush();
		em.clear();

		//when
		PageRequest pageRequest = PageRequest.of(0, 350);

		long beforeTime = System.currentTimeMillis();
		Slice<SpotPageResponse> old = spotQuerydslRepository.findPageOldBySpotName("관광", pageRequest);
		long afterTime = System.currentTimeMillis();
		long secDiffTime = (afterTime - beforeTime);
		log.info("시간차이(m) 평범 : {}", secDiffTime); //113
		// log.info("old = {}",old.getContent());

		em.flush();
		em.clear();

		long beforeTime1 = System.currentTimeMillis();
		spotRepository.findPageBySpotName("관광", 0l,
			pageRequest);
		long afterTime1 = System.currentTimeMillis();
		long secDiffTime1 = (afterTime1 - beforeTime1);
		log.info("시간차이(m) 최적화 : {}", secDiffTime1); //28



		//then
	}

	@Test
	public void 관광지_이름_검색내역_다음페이지_없음() throws Exception {
		//given
		List<Spot> spots = spotJpaRepository.saveAll(
			List.of(givenSpotWithName("관광지1"), givenSpotWithName("관광지2"), givenSpotWithName("관광지3"))
		);

		//when
		PageRequest pageRequest = PageRequest.of(0, 20);
		Slice<SpotPageResponse> spotPageResponses = spotRepository.findPageBySpotName("관광", 0l,
			pageRequest);

		//then
		assertThat(spotPageResponses.getContent().size()).isEqualTo(3);
		assertThat(spotPageResponses.hasNext()).isFalse();
	}

	@Test
	public void location으로_spot별_점수와함께_spot찾기() throws Exception {
		//given
		List<Spot> spots = createSpotWithScore();

		//when
		List<SpotWithCategoryScoreDto> spotWithCategoryScoreDtos = spotRepository.findWithCategoryScoreByLocation(
			List.of(Location.Aewol_eup, Location.Daejeong_eup, Location.Jeju_si),
			Category.SURROUND
		);

		//then
		assertThat(spotWithCategoryScoreDtos.size()).isEqualTo(3);
		assertThat(spotWithCategoryScoreDtos.get(0).getCategoryScore()).isEqualTo(30d);
	}

	private List<Spot> createSpotWithScore() {
		List<Spot> spots = spotJpaRepository.saveAll(
			List.of(givenSpotWithLocation(Location.Aewol_eup), givenSpotWithLocation(Location.Daejeong_eup),
				givenSpotWithLocation(Location.Jeju_si)
			)
		);
		List<Score> scores = scoreJpaRepository.saveAll(
			List.of(givenScore(spots.get(0).getId()), givenScore(spots.get(1).getId()),
				givenScore(spots.get(2).getId()))
		);
		return spots;
	}

	@Test
	public void location들에_해당하는_spot들_찾기() throws Exception {
		//given
		List<Spot> spots = createSpotByLocation();

		//when
		List<Long> spotIds = spots.stream().map(s -> s.getId()).toList();
		List<Long> targetSpotIds = spotRepository.findSpotIdsByLocations(
			List.of(Location.Aewol_eup, Location.Daejeong_eup, Location.Jeju_si)
		);

		//then
		assertThat(targetSpotIds.size()).isEqualTo(3);
	}

}
