package com.koko.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.koko.exception.BusinessException;
import com.koko.exception.ResourceNotFoundException;
import com.koko.mapper.ArticleMapper;
import com.koko.pojo.Article;
import com.koko.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper mapper;

    /* 分页查询 */
    @Override
    public List<Article> queryByPage(Integer page, Integer pageSize) {

        //1. 设置分页参数
        PageHelper.startPage(page, pageSize);

        //2. 查询数据库。sql语句不要加分号!!!
        List<Article> list = mapper.queryByPage();

        //3. 封装到pageInfo
        PageInfo<Article> pageInfo = new PageInfo<>(list);

        //4. 返回结果
        return pageInfo.getList();
    }

    /* 文章列表查询 */
    @Override
    public List<Article> queryList(Long cId) {
        List<Article> list = mapper.queryList(cId);
        if (list == null || list.size()==0) {
            throw new ResourceNotFoundException("此分类的文章为空");
        }

        return list;
    }

    /* 文章详情查询 */
    @Override
    public Article queryDetails(Long id) {
        Article article = mapper.queryDetails(id);

        //如果文章不存在，抛异常
        if (article == null) {
            throw new ResourceNotFoundException("文章不存在");
        }

        //如果文章存在，将密码置空，并返回
        article.getUser().setPassword(null);

        return article;
    }

    /* 文章新增 */
    @Override
    public void insertArticle(Article article) {
        /* 基本的参数校验在controller完成 */

        article.setCreateTime(new Date());
        article.setUpdateTime(new Date());
        int affectedRows = mapper.insert(article);

        //新增失败
        if (affectedRows == 0) {
            throw new BusinessException(500, "新增失败，请稍后再试");
        }

    }

    /* 文章修改 */
    @Override
    public void updateArticle(Article article) {
        /* 先确定文章存在，再修改 */
        if (mapper.queryDetails(article.getId()) == null) {
            throw new ResourceNotFoundException("文章不存在或已被删除");
        }

        article.setUpdateTime(new Date());
        int affectedRows = mapper.update(article);

        //修改失败
        if (affectedRows == 0) {
            throw new ResourceNotFoundException("修改失败，数据可能未变动");
        }

    }

    /* 文章删除 */
    @Override
    public void deleteArticle(Long id) {
        int affectedRows = mapper.delete(id);

        //删除失败
        if (affectedRows == 0) {
            throw new ResourceNotFoundException("删除失败，文章不存在或已被删除");
        }

    }
}
