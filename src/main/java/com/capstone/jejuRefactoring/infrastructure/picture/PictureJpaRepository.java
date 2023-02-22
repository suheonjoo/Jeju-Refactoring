package com.capstone.jejuRefactoring.infrastructure.picture;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capstone.jejuRefactoring.domain.picture.Picture;
import com.capstone.jejuRefactoring.domain.preference.MemberSpotTag;

public interface PictureJpaRepository extends JpaRepository<Picture, Long> {
}
