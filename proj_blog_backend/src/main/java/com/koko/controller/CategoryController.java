package com.koko.controller;

import com.koko.pojo.Category;
import com.koko.service.CategoryService;
import com.koko.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {
    @Autowired
    private CategoryService service;

    @GetMapping("/list")
    public Result<List<Category>> queryAll() {
        List<Category> list = service.queryAll();
        return Result.ok(list);
    }

    @GetMapping("/page")
    public Result<HashMap<String, Object>> queryByPage(
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "5") Integer pageSize,
            @RequestParam(required = false) String keyword) {

        HashMap<String, Object> data = service.queryByPage(page, pageSize, keyword);

        return Result.ok(data);
    }

    @GetMapping("/{id}")
    public Result<Category> queryDetails(@PathVariable Long id) {
        Category category = service.queryDetails(id);
        return Result.ok(category);
    }

    @PostMapping
    public Result<Object> insertCategory(@Validated @RequestBody Category category) {
        service.insert(category);
        return Result.ok();
    }

    @PutMapping
    public Result<Object> updateCategory(@Validated @RequestBody Category category) {
        service.update(category);
        return Result.ok();
    }


    @DeleteMapping("/{id}")
    public Result<Category> deleteCategory(@PathVariable Long id) {
        service.delete(id);
        return Result.ok();
    }


}
