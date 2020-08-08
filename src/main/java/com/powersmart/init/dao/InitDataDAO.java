/******************************************************************************
 * Copyright (C) 2017 ShenZhen Powerdata Information Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为深圳博安达开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、
 * 复制、修改或发布本软件.
 *****************************************************************************/

package com.powersmart.init.dao;

import com.powersmart.init.model.InitDataVO;
import com.powersmart.study.model.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
    public int[] batchInsert(String tableName, List<InitDataVO> lst) {
        StringBuilder sbSQL = new StringBuilder(50);
        sbSQL.append("insert into "+ tableName +" (name, phone, age) values (?, ?, ?)");
        int[] result = jdbcTemplate.batchUpdate(sbSQL.toString(), new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement pstmt, int idx) throws SQLException {
                InitDataVO vo = lst.get(idx);
                pstmt.setString(1, vo.getName());
                pstmt.setString(2, vo.getPhone());
                pstmt.setInt(3, vo.getAge());
            }

            @Override
            public int getBatchSize() {
                return lst.size();
            }
        });
        return result;
    }

}
