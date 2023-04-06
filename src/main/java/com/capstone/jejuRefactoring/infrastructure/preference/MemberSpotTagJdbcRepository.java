package com.capstone.jejuRefactoring.infrastructure.preference;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.capstone.jejuRefactoring.domain.preference.MemberSpotTag;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberSpotTagJdbcRepository {
	private final JdbcTemplate jdbcTemplate;

	public void saveAll(List<MemberSpotTag> memberSpotTags) {
		jdbcTemplate.batchUpdate("insert into member_spot_tag " +
				"(member_id, spot_id)" +
				" values(?,?)",
			new BatchPreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					ps.setLong(1,memberSpotTags.get(i).getMember().getId());
					ps.setLong(2,memberSpotTags.get(i).getSpot().getId());
				}
				@Override
				public int getBatchSize() {
					return memberSpotTags.size();
				}
			}
		);
	}
}
