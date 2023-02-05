package com.capstone.jejuRefactoring.infrastructure.auth.respository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capstone.jejuRefactoring.domain.auth.Member;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {
	Optional<Member> findOptionByEmail(String email);
}























































