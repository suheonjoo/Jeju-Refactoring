package com.capstone.jejuRefactoring.support;

import org.springframework.context.annotation.Import;

import com.capstone.jejuRefactoring.config.QuerydslConfig;
import com.capstone.jejuRefactoring.infrastructure.picture.PictureQuerydslRepository;
import com.capstone.jejuRefactoring.infrastructure.picture.PictureRepositoryImpl;
import com.capstone.jejuRefactoring.infrastructure.preference.MemberSpotTagJdbcRepository;
import com.capstone.jejuRefactoring.infrastructure.preference.MemberSpotTagQuerydslRepository;
import com.capstone.jejuRefactoring.infrastructure.preference.MemberSpotTagRepositoryImpl;
import com.capstone.jejuRefactoring.infrastructure.preference.ScoreQuerydslRepository;
import com.capstone.jejuRefactoring.infrastructure.preference.ScoreRepositoryImpl;
import com.capstone.jejuRefactoring.infrastructure.preference.SpotLikeTagRepositoryImpl;
import com.capstone.jejuRefactoring.infrastructure.review.ReviewQuerydslRepository;
import com.capstone.jejuRefactoring.infrastructure.review.ReviewRepositoryImpl;
import com.capstone.jejuRefactoring.infrastructure.spot.PictureTagQuerydslRepository;
import com.capstone.jejuRefactoring.infrastructure.spot.PictureTagRepositoryImpl;
import com.capstone.jejuRefactoring.infrastructure.spot.SpotQuerydslRepository;
import com.capstone.jejuRefactoring.infrastructure.spot.SpotRepository;
import com.capstone.jejuRefactoring.infrastructure.wishList.WishListRepositoryImpl;
import com.capstone.jejuRefactoring.infrastructure.wishList.WishListSpotTagRepositoryImpl;

//@import: 해당 클래스 스프링 빈으로 등록하기, (빈으로 등록해주는 방법이다 ㅎ)
@Import({
	QuerydslConfig.class,
	PictureQuerydslRepository.class,
	PictureRepositoryImpl.class,
	MemberSpotTagQuerydslRepository.class,
	MemberSpotTagJdbcRepository.class,
	MemberSpotTagRepositoryImpl.class,
	ScoreQuerydslRepository.class,
	ScoreRepositoryImpl.class,
	ReviewQuerydslRepository.class,
	ReviewRepositoryImpl.class,
	PictureTagQuerydslRepository.class,
	PictureTagRepositoryImpl.class,
	SpotQuerydslRepository.class,
	SpotRepository.class,
	SpotLikeTagRepositoryImpl.class,
	WishListRepositoryImpl.class,
	WishListSpotTagRepositoryImpl.class
})
public abstract class QuerydslRepositoryTest extends JpaRepositoryTest {
}

