package com.koko.mapper;

import com.koko.pojo.Article;

import java.util.List;

public interface ArticleMapper {
    List<Article> queryByPage();

    List<Article> queryList(Long cId);

    Article queryDetails(Long id);

    int insert(Article article);

    int update(Article article);

    int delete(Long id);

}
