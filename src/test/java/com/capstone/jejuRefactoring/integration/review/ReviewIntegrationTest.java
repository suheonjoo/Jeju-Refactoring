package com.capstone.jejuRefactoring.integration.review;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.capstone.jejuRefactoring.domain.wishList.repository.WishListRepository;
import com.capstone.jejuRefactoring.domain.wishList.repository.WishListSpotTagRepository;
import com.capstone.jejuRefactoring.support.IntegrationTest;

public class ReviewIntegrationTest extends IntegrationTest {
	private final String URL = "/api/v1/members/";

	@Autowired
	WishListRepository wishListRepository;
	@Autowired
	WishListSpotTagRepository wishListSpotTagRepository;

	@Test
	public void 페이진된_광광지의_리뷰_가져오기() throws Exception{
	    //given

	    //when

	    //then
	}


}
