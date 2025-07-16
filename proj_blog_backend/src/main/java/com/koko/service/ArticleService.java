package com.koko.service;

import com.koko.pojo.Article;
import com.koko.utils.Result;

import java.util.List;

public interface ArticleService {

    List<Article> queryByPage(Integer page, Integer pageSize);

    List<Article> queryList(Long cId);

    Article queryDetails(Long id);

    void insertArticle(Article article);

    void updateArticle(Article article);

    void deleteArticle(Long id);

}
