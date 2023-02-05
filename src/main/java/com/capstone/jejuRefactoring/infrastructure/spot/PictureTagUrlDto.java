package com.capstone.jejuRefactoring.infrastructure.spot;



import jakarta.persistence.Lob;
import lombok.Data;

@Data
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
