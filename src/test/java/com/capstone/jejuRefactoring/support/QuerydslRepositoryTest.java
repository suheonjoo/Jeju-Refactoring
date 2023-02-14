package com.capstone.jejuRefactoring.support;

import org.springframework.context.annotation.Import;

import com.capstone.jejuRefactoring.config.QuerydslConfig;
import com.capstone.jejuRefactoring.infrastructure.picture.PictureQuerydslRepository;
import com.capstone.jejuRefactoring.infrastructure.picture.PictureRepositoryImpl;
import com.capstone.jejuRefactoring.infrastructure.priority.MemberSpotTagQuerydslRepository;
import com.capstone.jejuRefactoring.infrastructure.priority.MemberSpotTagRepositoryImpl;
import com.capstone.jejuRefactoring.infrastructure.priority.ScoreQuerydslRepository;
import com.capstone.jejuRefactoring.infrastructure.priority.ScoreRepositoryImpl;
import com.capstone.jejuRefactoring.infrastructure.review.ReviewQuerydslRepository;
import com.capstone.jejuRefactoring.infrastructure.review.ReviewRepositoryImpl;
import com.capstone.jejuRefactoring.infrastructure.spot.PictureTagQuerydslRepository;
import com.capstone.jejuRefactoring.infrastructure.spot.PictureTagRepositoryImpl;
import com.capstone.jejuRefactoring.infrastructure.spot.SpotQuerydslRepository;
import com.capstone.jejuRefactoring.infrastructure.spot.SpotRepositoryImpl;
import com.capstone.jejuRefactoring.infrastructure.wishList.WishListRepositoryImpl;
import com.capstone.jejuRefactoring.infrastructure.wishList.WishListSpotTagRepositoryImpl;

@Import({
	QuerydslConfig.class,
	PictureQuerydslRepository.class,
	PictureRepositoryImpl.class,
	MemberSpotTagQuerydslRepository.class,
	MemberSpotTagRepositoryImpl.class,
	ScoreQuerydslRepository.class,
	ScoreRepositoryImpl.class,
	ReviewQuerydslRepository.class,
	ReviewRepositoryImpl.class,
	PictureTagQuerydslRepository.class,
	PictureTagRepositoryImpl.class,
	SpotQuerydslRepository.class,
	SpotRepositoryImpl.class,
	WishListRepositoryImpl.class,
	WishListSpotTagRepositoryImpl.class
})
public abstract class QuerydslRepositoryTest extends JpaRepositoryTest{
}

