/******************************************************************************
 * Copyright (C) 2017 ShenZhen Powerdata Information Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为深圳博安达开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、
 * 复制、修改或发布本软件.
 *****************************************************************************/

package com.powersmart.init.model;

import lombok.Data;

/**
 * @author 冰飞江南
 * @Title:
 * @history 2020年08月08日 冰飞江南 新建
 * @since JDK1.8
 */
@Data
public class InitDataVO {

    /**
     * 自增ID
     */
    private int id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 年龄
     */
    private int age;

}
