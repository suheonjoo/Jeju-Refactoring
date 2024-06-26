package com.capstone.jejuRefactoring.domain.wishList;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.capstone.jejuRefactoring.domain.auth.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Cleanup;
import lombok.Getter;

@Entity
@Getter
@Builder
@Table(name = "wishList")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class WishList {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "wishList_id")
	private Long id;

	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@Builder.Default
	@OneToMany(mappedBy = "wishList")
	private List<WishListSpotTag> wishListSpotTages = new ArrayList<>();

	protected WishList() {
	}

	// public static WishList of(String wishListName) {
	// 	return WishList.builder()
	// 		.name(wishListName)
	// 		.wishListSpotTages()
	// 		.build();
	// }

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		WishList wishList = (WishList)o;
		return Objects.equals(getId(), wishList.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}
}

