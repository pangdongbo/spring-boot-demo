/******************************************************************************
 * Copyright (C) 2017 ShenZhen Powerdata Information Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为深圳博安达开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、
 * 复制、修改或发布本软件.
 *****************************************************************************/

package com.powersmart.study.service.impl;

import com.powersmart.study.dao.jdbc.DemoDAO;
import com.powersmart.study.model.UserVO;
import com.powersmart.study.service.IDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author 冰飞江南
 * @Title:
 * @history 2020年04月19日 冰飞江南 新建
 * @since JDK1.8
 */
@Service
public class DemoServiceImpl implements IDemoService {

    @Autowired
    private DemoDAO dao;

    @Override
    public List<Map<String, Object>> queryAll4Map() {
        return dao.queryAll4Map();
    }

    @Override
    public List<UserVO> queryAll4VO() {
        return dao.queryAll4VO();
    }

    @Override
    public List<UserVO> queryByOtherDS() {
        return dao.queryByOtherDS();
    }

    @Override
    public List<UserVO> query(UserVO vo) {
        return dao.query(vo);
    }

    @Override
    public UserVO getUserById(int id) {
        return dao.getUserById(id);
    }

    @Override
    public UserVO insert(UserVO vo) {
        UserVO v = dao.insert(vo);
        int i = 0;
        if (1 / i == 0) {
            System.out.println(1);
        }
        return v;
    }

    @Override
    public UserVO insertByNameParameter(UserVO vo) {
        return dao.insertByNameParameter(vo);
    }

    @Override
    public int[] batchInsert(List<UserVO> lst) {
        return dao.batchInsert(lst);
    }
}
