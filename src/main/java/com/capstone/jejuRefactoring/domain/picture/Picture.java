package com.capstone.jejuRefactoring.domain.picture;

import com.capstone.jejuRefactoring.domain.spot.Spot;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@Builder
@Table(name = "picture")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Picture {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "picture_id")
	private Long id;

	@Lob
	@Column(name = "picture_url")
	private String url;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "spot_id")
	private Spot spot;

	protected Picture() {

	}

	@Override
	public String toString() {
		return "Picture{" +
			"id=" + id +
			", url='" + url + '\'' +
			", spot=" + spot +
			'}';
	}
}
