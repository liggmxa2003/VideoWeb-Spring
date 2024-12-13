package com.ligg.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ligg.mapper.ArticleMapper;
import com.ligg.pojo.Article;
import com.ligg.pojo.PageBean;
import com.ligg.service.ArticleService;
import com.ligg.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    ArticleMapper articleMapper;
    // 添加文章
    @Override
    public void add(Article article) {
        Map<String, Object> map = ThreadLocalUtil.get();
        article.setCreateUser((Integer) map.get("id"));
        articleMapper.add(article);
    }
    // 查询用户文章标题
    @Override
    public List<Article> findByTitle(Article article) {
        Map<String, Object> map = ThreadLocalUtil.get();
        article.setCreateUser((Integer) map.get("id"));
        return articleMapper.findByTitle(article);
    }
    // 更新文章
    @Override
    public void update(Article article) {
        articleMapper.update(article);
    }

    @Override
    public Article findByUserAndIdArticleId(Article article) {
        Map<String, Object> map = ThreadLocalUtil.get();
        article.setCreateUser((Integer) map.get("id"));
        return articleMapper.findByUserAndIdArticleId(article);
    }

    @Override
    public void delete(Article article) {
        articleMapper.delete(article);
    }

    // 分页查询文章列表
    @Override
    public PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state) {
        // 封装分页数据
        PageBean<Article> pb = new PageBean<>();
        //开启分页查询
        PageHelper.startPage(pageNum, pageSize);
        //调用mapper查询
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        List<Article> as = articleMapper.list(userId,categoryId,state);
        //Page提供了方法，可以获取PageHelper分页查询后得到的总记录条数和当前页数据
        Page<Article> p = (Page<Article>) as;
        //把数据填充到PageBean中
        pb.setItems(p.getResult());
        pb.setTotal(p.getTotal());
        return pb;
    }

}
