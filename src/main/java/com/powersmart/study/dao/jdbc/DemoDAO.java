package com.powersmart.study.dao.jdbc;

import cn.hutool.core.util.StrUtil;
import com.powersmart.study.model.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Repository
public class DemoDAO {

	/**
	 * jdbc对象
	 */
	@Autowired
	@Qualifier("DefaultJdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	/**
	 * 其他数据源
	 */
	@Autowired
	@Qualifier("OtherJdbcTemplate") //@Resource(name="otherJdbcTemplate")
	private JdbcTemplate otherJdbcTemplate;

	@Autowired
	@Qualifier("DefaultNamedParameterJdbcTemplate")
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	/**
	 * 查询所有用户，并以Map的方式返回
	 * @return
	 */
	public List<Map<String, Object>> queryAll4Map() {
		return jdbcTemplate.queryForList("select * from t_study_user");
	}

	/**
	 * 查询所有用户，并以VO的方式返回
	 * @return
	 */
	public List<UserVO> queryAll4VO() {
		return jdbcTemplate.query("select * from t_study_user", new BeanPropertyRowMapper<UserVO>(UserVO.class));
	}

	/**
	 * 使用其他数据源的示例
	 * @return
	 */
	public List<UserVO> queryByOtherDS() {
		return otherJdbcTemplate.query("select * from t_study_user", new BeanPropertyRowMapper<UserVO>(UserVO.class));
	}

	/**
	 * 根据条件查询用户
	 * @return
	 */
	public List<UserVO> query(UserVO vo) {
		StringBuilder sbSQL = new StringBuilder(100);
		List<Object> lstQC = new LinkedList<Object>();
		sbSQL.append("select * from t_study_user where 1 = 1");
		if (StrUtil.isNotBlank(vo.getAccount())) {
			vo.setAccount("%" + vo.getAccount() + "%");
			sbSQL.append(" and account like ?");
			lstQC.add(vo.getAccount());
		}
		if (StrUtil.isNotBlank(vo.getName())) {
			vo.setName("%" + vo.getName() + "%");
			sbSQL.append(" and name like ?");
			lstQC.add(vo.getName());
		}
		return jdbcTemplate.query(sbSQL.toString(), lstQC.toArray(), new BeanPropertyRowMapper<UserVO>(UserVO.class));
	}

	/**
	 * 根据ID获取用户
	 * @return
	 */
	public UserVO getUserById(int id) {
		return jdbcTemplate.queryForObject("select * from t_study_user where id = ?", new BeanPropertyRowMapper<UserVO>(UserVO.class), id);
	}

	/**
	 * 添加用户（方法一）
	 * @return
	 */
	public UserVO insert(UserVO vo) {
		vo.setId(System.currentTimeMillis());
		StringBuilder sbSQL = new StringBuilder(100);
		sbSQL.append("insert into t_study_user (id, account, password, name, sex, birthday, phone, adress)")
				.append(" values (?, ?, ?, ?, ?, ?, ?, ?)");
		jdbcTemplate.update(sbSQL.toString(), new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement pstmt) throws SQLException {
				pstmt.setLong(1, vo.getId());
				pstmt.setString(2, vo.getAccount());
				pstmt.setString(3, vo.getPassword());
				pstmt.setString(4,vo.getName());
				pstmt.setInt(5, vo.getSex());
				pstmt.setDate(6, new Date(vo.getBirthday().getTime()));
				pstmt.setString(7, vo.getPhone());
				pstmt.setString(8, vo.getAdress());
			}
		});
		return vo;
	}

	/**
	 * 添加用户（方法二）
	 * @return
	 */
	public UserVO insertByNameParameter(UserVO vo) {
		vo.setId(System.currentTimeMillis());
		StringBuilder sbSQL = new StringBuilder(100);
		sbSQL.append("insert into t_study_user (id, account, password, name, sex, birthday, phone, adress)")
				.append(" values (:id, :account, :password, :name, :sex, :birthday, :phone, :adress)");
		namedParameterJdbcTemplate.update(sbSQL.toString(), new BeanPropertySqlParameterSource(vo));
		return vo;
	}

	/**
	 * 批量添加用户
	 * @return
	 */
	public int[] batchInsert(List<UserVO> lst) {
		for (UserVO vo : lst) {
			try {
				TimeUnit.MILLISECONDS.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			vo.setId(System.currentTimeMillis());
		}
		StringBuilder sbSQL = new StringBuilder(100);
		sbSQL.append("insert into t_study_user (id, account, password, name, sex, birthday, phone, adress)")
				.append(" values (?, ?, ?, ?, ?, ?, ?, ?)");
		int[] result = jdbcTemplate.batchUpdate(sbSQL.toString(), new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement pstmt, int idx) throws SQLException {
				UserVO vo = lst.get(idx);
				pstmt.setLong(1, vo.getId());
				pstmt.setString(2, vo.getAccount());
				pstmt.setString(3, vo.getPassword());
				pstmt.setString(4,vo.getName());
				pstmt.setInt(5, vo.getSex());
				pstmt.setDate(6, new Date(vo.getBirthday().getTime()));
				pstmt.setString(7, vo.getPhone());
				pstmt.setString(8, vo.getAdress());
			}

			@Override
			public int getBatchSize() {
				return lst.size();
			}
		});
		return result;
	}
}
