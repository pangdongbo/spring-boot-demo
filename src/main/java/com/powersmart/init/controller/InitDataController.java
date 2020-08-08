/******************************************************************************
 * Copyright (C) 2017 ShenZhen Powerdata Information Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为深圳博安达开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、
 * 复制、修改或发布本软件.
 *****************************************************************************/

package com.powersmart.init.controller;

import com.powersmart.init.model.InitDataVO;
import com.powersmart.init.service.InitDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

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

        System.out.println("开始初始化数据了。");

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    List<InitDataVO> lst = new ArrayList<InitDataVO>(10000);
                    for (int j = 0; j < 10000; j++) {
                        InitDataVO vo = new InitDataVO();
                        vo.setName("" + System.currentTimeMillis());
                        vo.setPhone("" + System.nanoTime());
                        vo.setAge(Math.abs(new Random().nextInt(100)));
                        lst.add(vo);
                    }
                    service.initData("t_name_idx", lst);
                    service.initData("t_name_phone_idx", lst);
                    service.initData("t_name_phone_age_idx", lst);
                    System.out.print("完成插入" + (i + 1) * 10000 + "条记录");
                }

            }
        }).start();

        return "ok";
    }

}
