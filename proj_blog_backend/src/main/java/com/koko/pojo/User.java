package com.koko.pojo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

@Data
public class User {

    private Long uId; //用户id

    @NotBlank(message = "用户名不能为空")
    @Size(min = 2, max = 10, message = "用户名必须2-10位")
    private String username;//用户名称

    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 16, message = "用户名必须6-16位")
    private String password;//密码

    @NotBlank(message = "手机号不能为空")
    @Size(min = 11, max = 11, message = "手机号必须是11位")
    private String phone;//手机号

    @Size(max = 80, message = "简介不能超过80个字符")
    private String introduction;//个人介绍

    private Date createTime;

    private Date updateTime;

}