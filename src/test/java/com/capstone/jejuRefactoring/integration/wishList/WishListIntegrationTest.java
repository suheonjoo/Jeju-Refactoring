package com.capstone.jejuRefactoring.integration.wishList;

import static com.capstone.jejuRefactoring.config.security.provider.JwtTokenProvider.*;
import static com.capstone.jejuRefactoring.support.helper.MemberGivenHelper.*;
import static com.capstone.jejuRefactoring.support.helper.SpotGivenHelper.*;
import static com.capstone.jejuRefactoring.support.helper.WishListGivenHelper.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.capstone.jejuRefactoring.domain.auth.Member;
import com.capstone.jejuRefactoring.domain.spot.PictureTag;
import com.capstone.jejuRefactoring.domain.spot.Spot;
import com.capstone.jejuRefactoring.domain.spot.repository.SpotRepository;
import com.capstone.jejuRefactoring.domain.wishList.WishList;
import com.capstone.jejuRefactoring.infrastructure.auth.respository.MemberJpaRepository;
import com.capstone.jejuRefactoring.infrastructure.spot.PictureTagJpaRepository;
import com.capstone.jejuRefactoring.infrastructure.spot.SpotJpaRepository;
import com.capstone.jejuRefactoring.infrastructure.wishList.WishListJpaRepository;
import com.capstone.jejuRefactoring.infrastructure.wishList.WishListSpotTagJpaRepository;
import com.capstone.jejuRefactoring.presentation.wishList.dto.request.WishListModifyRequest;
import com.capstone.jejuRefactoring.presentation.wishList.dto.request.WishListSaveRequest;
import com.capstone.jejuRefactoring.support.IntegrationTest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WishListIntegrationTest extends IntegrationTest {

	private final String URL = "/api/v1/wishLists"; //이거 url 마지막에 "/" 가 붙어도 에러남 ㅋ
	@Autowired
	WishListJpaRepository wishListJpaRepository;
	@Autowired
	WishListSpotTagJpaRepository wishListSpotTagJpaRepository;
	@Autowired
	MemberJpaRepository memberJpaRepository;
	@Autowired
	SpotJpaRepository spotJpaRepository;
	@Autowired
	PictureTagJpaRepository pictureTagJpaRepository;

	@Autowired
	SpotRepository spotRepository;
	@PersistenceContext
	EntityManager em;

	@Test
	public void 위시리스트_생성하기() throws Exception{
	    //given
		Member member = memberJpaRepository.save(givenMember());
		WishList wishList = wishListJpaRepository.save(givenWishListWithWishListName(member.getId(), "관광지이름1"));
		WishListSaveRequest wishListSaveRequest = givenWishListSaveRequest(wishList.getName(), member.getId());

		// when then
		mockMvc.perform(MockMvcRequestBuilders.post(URL +"/"+wishList.getName().toString())
				.header(ACCESS_TOKEN, TOKEN_TYPE + createAccessToken(member))
				.content(createJson(wishListSaveRequest))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
		;
	}
	
	@Test
	public void 특정_위시리스트_삭제하기() throws Exception{
	    //given
		Member member = memberJpaRepository.save(givenMember());
		WishList wishList = wishListJpaRepository.save(givenWishListWithWishListName(member.getId(), "관광지이름1"));

	    //then
		mockMvc.perform(MockMvcRequestBuilders.delete(URL +"/" + wishList.getId().toString())
				.header(ACCESS_TOKEN, TOKEN_TYPE + createAccessToken(member))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
		;
	}
	
	@Test
	public void 위시리스트_안에_특정_관광지_삭제하기() throws Exception{
		//given
		Member member = memberJpaRepository.save(givenMember());
		WishList wishList = wishListJpaRepository.save(givenWishListWithWishListName(member.getId(), "관광지이름1"));
		Spot spot = spotJpaRepository.save(givenSpot());

		//then
		mockMvc.perform(MockMvcRequestBuilders.delete(URL +"/" + wishList.getId().toString()
				+"/"+spot.getId().toString()
				)
				.header(ACCESS_TOKEN, TOKEN_TYPE + createAccessToken(member))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
		;
	}
	
	@Test
	public void 특정_위시리스트_이름_수정하기() throws Exception{
	    //given
		Member member = memberJpaRepository.save(givenMember());
		WishList wishList = wishListJpaRepository.save(givenWishListWithWishListName(member.getId(), "관광지이름1"));
		// Spot spot = spotJpaRepository.save(givenSpot());
		WishListModifyRequest wishListModifyRequest = givenWishListModifyRequest(wishList.getId(), wishList.getName(),
			member.getId());

		//then
		mockMvc.perform(MockMvcRequestBuilders.patch(URL +"/" + wishList.getId().toString())
				.header(ACCESS_TOKEN, TOKEN_TYPE + createAccessToken(member))
				.content(createJson(wishListModifyRequest))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
		;
	}
	
	@Test
	public void 위시리스트_메인_페이지_보여주기() throws Exception{
		//given
		Member member = memberJpaRepository.save(givenMember());
		WishList wishList = wishListJpaRepository.save(givenWishListWithWishListName(member.getId(), "관광지이름1"));
		Spot spot = spotJpaRepository.save(givenSpot());
		wishListSpotTagJpaRepository.save(givenWishListSpotTag(spot.getId(), wishList.getId()));
		PictureTag pictureTag = pictureTagJpaRepository.save(givenPictureTagWithId(spot.getId()));

		//then
		mockMvc.perform(MockMvcRequestBuilders.get(URL)
				.header(ACCESS_TOKEN, TOKEN_TYPE + createAccessToken(member))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.memberId").value(member.getId()))
			.andExpect(jsonPath("$.data.wishListResponseDtos.size()").value(1))
			.andExpect(jsonPath("$.data.wishListResponseDtos[0].wishListName").value(wishList.getName()))
			.andExpect(jsonPath("$.data.wishListResponseDtos[0].pictureTagUrlDtos[0].idMin").value(pictureTag.getId()))
		;
	}

	@Test
	public void 관광지경로_페이지_보여주기() throws Exception{
	    //given
		Member member = memberJpaRepository.save(givenMember());
		log.info("member.getId() = {}", member.getId());
		WishList wishList = wishListJpaRepository.save(givenWishListWithWishListName(member.getId(), "관광지이름1"));
		Spot spot = spotJpaRepository.save(givenSpot());
		log.info("spot = {}", spot);
		wishListSpotTagJpaRepository.save(givenWishListSpotTag(spot.getId(), wishList.getId()));

		PictureTag pictureTag = pictureTagJpaRepository.save(givenPictureTagWithUrl(spot.getId(), "url111"));
		log.info("pictureTag = {}", pictureTag);


		List<Spot> spotJpaRepositoryAllById = spotJpaRepository.findAllById(List.of(spot.getId()));
		log.info("spotJpaRepositoryAllById = {}",spotJpaRepositoryAllById);
		List<PictureTag> bySpotIds = pictureTagJpaRepository.findBySpotIds(List.of(spot.getId()));
		log.info("bySpotIds = {}", bySpotIds);

		List<Spot> spots = spotRepository.findBySpotIdsWithFetchJoin(List.of(spot.getId()));

		List<Spot> result = em.createQuery(
				"select s from Spot s join fetch s.pictureTags where s.id =:spotId", Spot.class)
			.setParameter("spotId", spot.getId())
			.getResultList();
		log.info("spots = {}",spots.get(0));
		log.info("spots.get(0).getPictureTags().size() = {}",spots.get(0).getPictureTags().size());

		log.info("result.get(0).getPictureTags().size() = {}", result.get(0).getPictureTags().size());

		// log.info("spots.get(0).getPictureTags().get(0)= {} ", spots.get(0).getPictureTags().get(0));


		List list = em.createQuery(
				"select  s.id, pt.url from Spot s inner join PictureTag pt on pt.spot.id = s.id  where s.id =:spotId")
			.setParameter("spotId", spot.getId())
			.getResultList();
		log.info("dtos.get(0)= {}",list.get(0));
		log.info("list.size() = {}",list.size());

		// List<Spot> result = em.createQuery("select s from Spot s join fetch s.pictureTags where s.id =:spotId",
		// 		Spot.class)
		// 	.setParameter("spotId", spot.getId())
		// 	.getResultList();
		// log.info("result = {}", result.get(0).getPictureTags().get(0));

		//when

	    //then
		// mockMvc.perform(MockMvcRequestBuilders.get(URL+"/"+wishList.getId().toString())
		// 		.header(ACCESS_TOKEN, TOKEN_TYPE + createAccessToken(member))
		// 		.contentType(MediaType.APPLICATION_JSON))
		// 	.andExpect(status().isOk())
		// 	.andExpect(jsonPath("$.data.wishListId").value(wishList.getId()))
		// 	// .andExpect(jsonPath("$.data.spotWithPictureTagDtos.size()").value(1))
		// 	// .andExpect(jsonPath("$.data.spotWithPictureTagDtos[0].id").value(spot.getId()))
		// 	// .andExpect(jsonPath("$.data.spotWithPictureTagDtos[0].pictureTagDto[0].url").value(pictureTag.getUrl()))
		// ;
	}
	static class dto{
		public Long sid;
		public Long ptid;

		public dto(Long sid, Long ptid) {
			this.sid = sid;
			this.ptid = ptid;
		}
	}


}
