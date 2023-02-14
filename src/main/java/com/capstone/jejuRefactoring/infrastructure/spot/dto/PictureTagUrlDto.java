package com.capstone.jejuRefactoring.infrastructure.spot.dto;



import jakarta.persistence.Lob;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PictureTagUrlDto {

	private Long spotId;
	@Lob
	private String url;

	private Integer idMin;

	public PictureTagUrlDto(Long spotId, String url, Integer idMin) {
		this.spotId = spotId;
		this.url = url;
		this.idMin = idMin;
	}


}
