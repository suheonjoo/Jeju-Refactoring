package com.capstone.jejuRefactoring.infrastructure.preference;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.capstone.jejuRefactoring.domain.preference.MemberSpotTag;

public interface MemberSpotTagJpaRepository extends JpaRepository<MemberSpotTag, Long> {

	@Query(value = "select mst from MemberSpotTag mst where mst.member.id = :memberId and mst.spot.id in :spotIds")
	List<MemberSpotTag> findByMemberIdAndSpotIds(@Param("memberId") Long memberId,
		@Param("spotIds") List<Long> spotIds);

	@Query(value = "select mst.IsLikeExist from MemberSpotTag mst where mst.member.id = :memberId and mst.spot.id = :spotId")
	boolean isSpotLikExistByMemberIdAndSpotId(@Param("spotId") Long spotId, @Param("memberId") Long memberId);

	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE MemberSpotTag mst SET mst.IsLikeExist = false WHERE mst.member.id = :memberId and mst.spot.id	= :spotId")
	void deleteSpotLikeByMemberIdAndSpotId(@Param("spotId") Long spotId, @Param("memberId") Long memberId);

	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE MemberSpotTag mst SET mst.IsLikeExist = true WHERE mst.member.id = :memberId and mst.spot.id	= :spotId")
	void createSpotLikeByMemberIdAndSpotId(@Param("spotId") Long spotId, @Param("memberId") Long memberId);
}
