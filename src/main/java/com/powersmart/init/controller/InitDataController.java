/******************************************************************************
 * Copyright (C) 2017 ShenZhen Powerdata Information Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为深圳博安达开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、
 * 复制、修改或发布本软件.
 *****************************************************************************/

package com.powersmart.init.controller;

import com.powersmart.init.model.DeptVO;
import com.powersmart.init.model.UserVO;
import com.powersmart.init.service.InitDataService;
import com.powersmart.init.utils.NameUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author 冰飞江南
 * @Title:
 * @history 2020年08月08日 冰飞江南 新建
 * @since JDK1.8
 */
@RestController
public class InitDataController {

    @Autowired
    private InitDataService service;

    @RequestMapping("/init")
    public String init() throws Exception {
        // initUser();
        initDept();
        return "ok";
    }

    private void initDept() {
        System.out.println("开始初始化部门数据了。");

        new Thread(new Runnable() {
            @Override
            public void run() {
                List<DeptVO> lst = new ArrayList<DeptVO>(1000);
                for (int i = 1; i < 1001; i++) {
                    String firstName = NameUtil.getFirstName();
                    String lastName = NameUtil.getLastName();
                    DeptVO vo = new DeptVO();
                    vo.setId(i);
                    vo.setName(firstName + lastName);
                    lst.add(vo);
                }
                long start = System.currentTimeMillis();
                service.initDept(lst);

                // 每秒插入数
                long ips = 1000 / ((System.currentTimeMillis() - start) / 1000);
                System.out.println("完成插入1000条记录，平均每秒插入" + ips + "条记录");
            }
        }).start();
    }

    private void initUser() {
        System.out.println("开始初始化用户数据了。");

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    List<UserVO> lst = new ArrayList<UserVO>(10000);
                    for (int j = 0; j < 10000; j++) {
                        String firstName = NameUtil.getFirstName();
                        String lastName = NameUtil.getLastName();
                        UserVO vo = new UserVO();
                        vo.setFirstName(firstName);
                        vo.setLastName(lastName);
                        vo.setName(firstName + lastName);
                        vo.setPhone("" + System.nanoTime());
                        vo.setAge(Math.abs(new Random().nextInt(100)));
                        vo.setSex(Math.abs(new Random().nextInt(2)));
                        vo.setDept(Math.abs(new Random().nextInt(1000)));
                        lst.add(vo);
                    }
                    long start = System.currentTimeMillis();
                    service.initUser(lst);
                    //service.initData("t_name_phone_idx", lst);
                    //service.initData("t_name_phone_age_idx", lst);

                    // 每秒插入数
                    long ips = 10000 / ((System.currentTimeMillis() - start) / 1000);

                    System.out.println("完成插入" + (i + 1) * 10000 + "条记录，平均每秒插入" + ips + "条记录");
                }

            }
        }).start();
    }

}
