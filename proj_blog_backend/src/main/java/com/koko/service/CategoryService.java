package com.koko.service;

import com.koko.pojo.Category;

import java.util.HashMap;
import java.util.List;

public interface CategoryService {
    List<Category> queryAll();

    HashMap<String, Object> queryByPage(Integer page, Integer pageSize, String keyword);

    Category queryDetails(Long id);

    void insert(Category category);

    void update(Category category);

    void delete(Long id);

}
