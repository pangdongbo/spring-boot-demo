package com.powersmart.study.service.impl;

import com.powersmart.study.dao.jdbc.DemoDAO;
import com.powersmart.study.model.UserVO;
import com.powersmart.study.service.IDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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

    @Cacheable(value = "user", key = "targetClass + methodName + #p0")
    @Override
    public UserVO getUserById(int id) {
        System.err.println("开查询数据库");
        return dao.getUserById(id);
    }

    @Override
    public UserVO insert(UserVO vo) throws Exception {
        return dao.insert(vo);
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
