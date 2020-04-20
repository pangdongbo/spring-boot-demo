package com.powersmart.study.service;

import com.powersmart.study.model.UserVO;

import java.util.List;
import java.util.Map;

/**
 * @author 冰飞江南
 * @Title: 用户服务接口
 * @history 2020年04月19日 冰飞江南 新建
 * @since JDK1.8
 */
public interface IDemoService {

    /**
     * 查询所有用户，并以Map的方式返回
     * @return
     */
    List<Map<String, Object>> queryAll4Map();

    /**
     * 查询所有用户，并以VO的方式返回
     * @return
     */
    List<UserVO> queryAll4VO();

    /**
     * 使用其他数据源的示例
     * @return
     */
    List<UserVO> queryByOtherDS();

    /**
     * 根据条件查询用户
     * @return
     */
    List<UserVO> query(UserVO vo);

    /**
     * 根据ID获取用户
     * @return
     */
    UserVO getUserById(int id);

    /**
     * 添加用户（方法一）
     * @return
     */
    UserVO insert(UserVO vo) throws Exception;

    /**
     * 添加用户（方法二）
     * @return
     */
    UserVO insertByNameParameter(UserVO vo);

    /**
     * 批量添加用户
     * @return
     */
    int[] batchInsert(List<UserVO> lst);
}
