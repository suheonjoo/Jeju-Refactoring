package com.capstone.jejuRefactoring.domain.spot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.capstone.jejuRefactoring.domain.priority.Score;
import com.capstone.jejuRefactoring.domain.review.Review;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@Builder
@Table(name = "spot", indexes = {@Index(name = "spot_name_index",columnList = "spot_name")})
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Spot {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "spot_id")
	private Long id;

	@Column(name = "spot_name")
	private String name;

	@Column(name = "spot_address")
	private String address;

	@Lob
	@Column(name = "spot_description")
	private String description;

	@Enumerated(EnumType.STRING)
	@Column(name = "spot_location")
	private Location location;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "score_id")
	private Score score;

	@OneToMany(mappedBy = "spot")
	@Builder.Default
	private List<Review> reviews = new ArrayList<>();

	@OneToMany(mappedBy = "spot")
	@Builder.Default
	private List<PictureTag> pictureTags = new ArrayList<>();


	protected Spot(){}

	public boolean isSameId(final Long id) {
		return Objects.equals(id, this.id);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Spot))
			return false;
		Spot spot = (Spot)o;
		return Objects.equals(id, spot.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
