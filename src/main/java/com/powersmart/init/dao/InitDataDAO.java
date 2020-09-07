/******************************************************************************
 * Copyright (C) 2017 ShenZhen Powerdata Information Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为深圳博安达开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、
 * 复制、修改或发布本软件.
 *****************************************************************************/

package com.powersmart.init.dao;

import com.powersmart.init.model.DeptVO;
import com.powersmart.init.model.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * @author 冰飞江南
 * @Title:
 * @history 2020年08月08日 冰飞江南 新建
 * @since JDK1.8
 */
@Repository
public class InitDataDAO {

    /**
     * jdbc对象
     */
    @Autowired
    @Qualifier("DefaultJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    /**
     * 批量添加用户
     * @return
     */
    public int[] batchInsertUser(List<UserVO> lst) {
        StringBuilder sbSQL = new StringBuilder(50);
        sbSQL.append("insert into t_user (name, phone, age, birthday, sex, dept, first_name, last_name) values (?, ?, ?, ?, ?, ?, ?, ?)");
        int[] result = jdbcTemplate.batchUpdate(sbSQL.toString(), new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement pstmt, int idx) throws SQLException {
                UserVO vo = lst.get(idx);
                pstmt.setString(1, vo.getName());
                pstmt.setString(2, vo.getPhone());
                pstmt.setInt(3, vo.getAge());
                pstmt.setDate(4, new Date(System.currentTimeMillis()));
                pstmt.setInt(5, vo.getSex());
                pstmt.setInt(6, vo.getDept());
                pstmt.setString(7, vo.getFirstName());
                pstmt.setString(8, vo.getLastName());
            }

            @Override
            public int getBatchSize() {
                return lst.size();
            }
        });
        return result;
    }

    /**
     * 批量添加部门
     * @return
     */
    public int[] batchInsertDept(List<DeptVO> lst) {
        StringBuilder sbSQL = new StringBuilder(50);
        sbSQL.append("insert into t_dept (id, name) values (?, ?)");
        int[] result = jdbcTemplate.batchUpdate(sbSQL.toString(), new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement pstmt, int idx) throws SQLException {
                DeptVO vo = lst.get(idx);
                pstmt.setLong(1, vo.getId());
                pstmt.setString(2, vo.getName());
                System.out.println(vo.getName());
            }

            @Override
            public int getBatchSize() {
                return lst.size();
            }
        });
        return result;
    }

}
