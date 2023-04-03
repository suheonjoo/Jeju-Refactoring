package com.capstone.jejuRefactoring.unit.common;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.Test;

import com.capstone.jejuRefactoring.common.metaDataBuilder.DefaultMetaDataBuilder;
import com.capstone.jejuRefactoring.common.metaDataBuilder.MetaData;
import com.capstone.jejuRefactoring.common.metaDataBuilder.MetaDataDetail;
import com.capstone.jejuRefactoring.common.metaDataBuilder.MetaDataDirector;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MetaDataDirectorTest {

	@Test
	public void 메타데이터_빌터_패턴_테스트() throws Exception {
		//given
		MetaDataDirector metaDataDirector = new MetaDataDirector(new DefaultMetaDataBuilder());

		//when
		metaDataDirector.categoryMetaData();
		List<MetaDataDetail> metaDataList = metaDataDirector.locationMetaData().getMetaDataList();

		//then
		assertThat(metaDataList.size()).isEqualTo(10);
		assertThat(metaDataList.get(9).getId()).isEqualTo(10);
	}
}
