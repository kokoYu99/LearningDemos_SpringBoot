package com.koko.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Category {

    private Long cId;
    private String cName; //分类名称
    private Date createTime;
    private Date updateTime;

}