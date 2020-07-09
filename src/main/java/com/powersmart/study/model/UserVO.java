package com.powersmart.study.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @Title: User实体
 * @author 冰飞江南
 * @history 2020年04月18日 冰飞江南 新建
 * @since JDK1.8
 */

@Data
public class UserVO implements Serializable {

    /**
     * 主键
     */
    private long id;

    /**
     * 登录账号
     */
    private String account;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private int sex;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 居住地址
     */
    private String adress;
}
