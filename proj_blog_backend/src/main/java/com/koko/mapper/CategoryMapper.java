package com.koko.mapper;

import com.koko.pojo.Category;

import java.util.List;

public interface CategoryMapper {
    List<Category> queryAll();

    List<Category> queryByPage(String keyword);

    Category queryDetails(Long id);

    int insert(Category category);

    int update(Category category);

    int delete(Long id);

    /*  校验类别是否已存在 */
    int isCNameExists(String cName);
}
