package com.powersmart.study.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class DemoDAO {

	/**
	 * jdbc对象
	 */
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 查询所有用户
	 * @return
	 */
	public List<Map<String, Object>> query() {
		return jdbcTemplate.queryForList("select * from t_xq_user");
	}
}
