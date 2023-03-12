package com.capstone.jejuRefactoring.integration.preference;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.capstone.jejuRefactoring.domain.wishList.repository.WishListRepository;
import com.capstone.jejuRefactoring.domain.wishList.repository.WishListSpotTagRepository;
import com.capstone.jejuRefactoring.support.IntegrationTest;

public class PreferenceIntegrationTest extends IntegrationTest {

	private final String URL = "/api/v1/members/";

	@Autowired
	WishListRepository wishListRepository;
	@Autowired
	WishListSpotTagRepository wishListSpotTagRepository;

	@Test
	public void 우선순위에따른_페이징된_관광지_순위_보여주기() throws Exception{
	    //given

	    //when

	    //then
	}

	@Test
	public void 지역별로_관광지_top10_보여주기() throws Exception{
	    //given

	    //when

	    //then
	}

	@Test
	public void 관광지_좋아요_누르기() throws Exception{
	    //given

	    //when

	    //then
	}

}
