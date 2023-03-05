package com.capstone.jejuRefactoring.domain.preference;

import java.util.Objects;

import com.capstone.jejuRefactoring.domain.auth.Member;
import com.capstone.jejuRefactoring.domain.spot.Spot;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@Builder
@Table(name = "member_spot_tag")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberSpotTag {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_spot_tag_id")
	private Long id;

	@Column(name = "personal_score")
	private Double personalScore;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "spot_id")
	private Spot spot;

	private Boolean IsLikeExist;

	protected MemberSpotTag() {
	}

	public static MemberSpotTag of(Long memberId, Long spotId) {
		return MemberSpotTag.builder()
			.member(Member.builder().id(memberId).build())
			.spot(Spot.builder().id(spotId).build())
			.build();
	}

	public void update(final Double memberPersonalScore) {
		if (memberPersonalScore != null) {
			this.personalScore = memberPersonalScore;
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		MemberSpotTag that = (MemberSpotTag)o;
		return Objects.equals(getId(), that.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}
}
