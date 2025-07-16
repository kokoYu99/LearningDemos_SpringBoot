package com.koko.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class User {

    private Long uId; //用户id
    private String username;//用户名称
    private String password;//密码
    private String phone;//手机号
    private String introduction;//个人介绍
    private Date createTime;
    private Date updateTime;

}