/******************************************************************************
 * Copyright (C) 2017 ShenZhen Powerdata Information Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为深圳博安达开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、
 * 复制、修改或发布本软件.
 *****************************************************************************/

package com.powersmart.init.service;

import com.powersmart.init.dao.InitDataDAO;
import com.powersmart.init.model.DeptVO;
import com.powersmart.init.model.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 冰飞江南
 * @Title:
 * @history 2020年08月08日 冰飞江南 新建
 * @since JDK1.8
 */
@Service
public class InitDataService {

    /**
     * 数据库操作对象
     */
    @Autowired
    private InitDataDAO dao;

    public void initUser(List<UserVO> lst) {

        dao.batchInsertUser(lst);

    }

    public void initDept(List<DeptVO> lst) {

        dao.batchInsertDept(lst);

    }

}
