package com.capstone.jejuRefactoring.infrastructure.review;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capstone.jejuRefactoring.domain.preference.MemberSpotTag;
import com.capstone.jejuRefactoring.domain.review.Review;

public interface ReviewJpaRepository extends JpaRepository<Review, Long> {
}
