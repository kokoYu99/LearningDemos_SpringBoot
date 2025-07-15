package com.koko.mapper;

import com.koko.pojo.User;
import org.apache.ibatis.annotations.Mapper;

//@Mapper //如果已经按包扫描@MapperScan，就不用此注解
public interface UserMapper {
    User queryById(Integer id);
}
