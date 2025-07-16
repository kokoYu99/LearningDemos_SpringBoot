package com.koko.controller;

import com.koko.pojo.Article;
import com.koko.service.ArticleService;
import com.koko.utils.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//描述Controller类
@Tag(name = "文章接口api")
@RestController
@RequestMapping("article")
public class ArticleController {
    @Autowired
    private ArticleService service;

    /* 文章分页查询 */
    //描述方法的功能
    @Operation(summary = "文章分页查询")
    //描述方法的全部参数
    @Parameters({
            //描述单个参数
            @Parameter(name = "page", description = "当前页"),
            @Parameter(name = "pageSize", description = "每页几条")
    })
    @GetMapping
    public Result<List<Article>> findByPage(@RequestParam(defaultValue = "1") Integer page,
                                            @RequestParam(defaultValue = "5") Integer pageSize) {
        List<Article> list = service.queryByPage(page, pageSize);
        return Result.ok(list);
    }

    /* 文章列表查询 */
    @GetMapping("/list")
    public Result<List<Article>> queryList(@RequestParam(required = false) Long cId) {
        List<Article> list = service.queryList(cId);
        return Result.ok(list);
    }

    /* 文章详情查询 */
    @GetMapping("/{id}")
    public Result<Article> queryDetails(@PathVariable Long id) {
        Article article = service.queryDetails(id);
        return Result.ok(article);
    }

    /* 文章新增 */
    @PostMapping
    public Result<Object> insertArticle(@Validated @RequestBody Article article) {
        //尝试新增
        service.insertArticle(article);
        //如果失败，在业务层就抛出异常，并返回失败信息(Result.fail()方法)

        //新增成功，返回成功信息
        return Result.ok();
    }

    /* 文章修改 */
    @PutMapping
    public Result<Object> updateArticle(@Validated @RequestBody Article article) {
        //尝试修改
        service.updateArticle(article);
        //如果失败，在业务层就抛出异常，并返回失败信息(Result.fail()方法)

        //修改成功，返回成功信息
        return Result.ok();
    }

    /* 文章删除 */
    @DeleteMapping("{id}")
    public Result<Object> deleteArticle(@PathVariable Long id) {
        //尝试修改
        service.deleteArticle(id);
        //如果失败，在业务层就抛出异常，并返回失败信息(Result.fail()方法)

        //删除成功，返回成功信息
        return Result.ok();
    }

}
