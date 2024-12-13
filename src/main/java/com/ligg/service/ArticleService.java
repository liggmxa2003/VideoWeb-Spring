package com.ligg.service;

import com.ligg.pojo.Article;
import com.ligg.pojo.PageBean;

import java.util.List;

public interface ArticleService {
    // 添加文章
    void add(Article article);
    // 判断标题是否重复
    List<Article> findByTitle(Article article);
    // 分页查询
    PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state);
    // 修改文章
    void update(Article article);
    // 判断用户是否是文章创建者
    Article findByUserAndIdArticleId(Article article);
    // 删除文章
    void delete(Article article);
}
