package com.capstone.jejuRefactoring.integration.wishList;

import org.springframework.beans.factory.annotation.Autowired;

import com.capstone.jejuRefactoring.domain.wishList.repository.WishListRepository;
import com.capstone.jejuRefactoring.domain.wishList.repository.WishListSpotTagRepository;
import com.capstone.jejuRefactoring.support.IntegrationTest;

public class WishListIntegrationTest extends IntegrationTest {

	private final String URL = "/api/v1/members/";

	@Autowired
	WishListRepository wishListRepository;
	@Autowired
	WishListSpotTagRepository wishListSpotTagRepository;



}
