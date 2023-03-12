package com.capstone.jejuRefactoring.integration.spot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.capstone.jejuRefactoring.domain.wishList.repository.WishListRepository;
import com.capstone.jejuRefactoring.domain.wishList.repository.WishListSpotTagRepository;
import com.capstone.jejuRefactoring.support.IntegrationTest;

public class SpotIntegrationTest extends IntegrationTest {

	private final String URL = "/api/v1/members/";

	@Autowired
	WishListRepository wishListRepository;
	@Autowired
	WishListSpotTagRepository wishListSpotTagRepository;

	@Test
	public void 특정관광지_세부내용_보여주기() throws Exception{
	    //given

	    //when

	    //then
	}

	@Test
	public void 관광지_이름으로_관광지_찾기() throws Exception{
	    //given

	    //when

	    //then
	}

	@Test
	public void 관광지메인페이지_메타데이터_가져오기() throws Exception{
	    //given

	    //when

	    //then
	}

}
