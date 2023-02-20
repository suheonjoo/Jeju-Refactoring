package com.capstone.jejuRefactoring.unit.domain.priority;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.capstone.jejuRefactoring.domain.priority.MemberSpotTag;

public class MemberSpotTagTest {

	@Test
	public void 정적_메서드_of로_생성() throws Exception{
	    //given
		MemberSpotTag memberSpotTag = MemberSpotTag.of(1l, 2l);

		//when

	    //then
		assertThat(memberSpotTag.getMember().getId()).isEqualTo(1l);
		assertThat(memberSpotTag.getSpot().getId()).isEqualTo(2l);
	}

}
