package com.capstone.jejuRefactoring.domain.wishList;

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
import jakarta.persistence.TableGenerator;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "wishList_spot_tag")
public class WishListSpotTag {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "wishList_spot_tag_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "wishList_id")
	private WishList wishList;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "spot_id")
	private Spot spot;

	/**
	 * wishListId, spotTagId 의 아이디만 참조한다
	 * @param wishListId
	 * @param spotId
	 * @return
	 */
	public static WishListSpotTag of(Long wishListId, Long spotId) {
		return WishListSpotTag.builder()
			.wishList(WishList.builder().id(wishListId).build())
			.spot(Spot.builder().id(spotId).build())
			.build();
	}

}
