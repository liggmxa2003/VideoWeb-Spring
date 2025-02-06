package com.ligg.controller;

import com.ligg.pojo.Article;
import com.ligg.pojo.PageBean;
import com.ligg.pojo.Result;
import com.ligg.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
//文章接口
@RestController
@RequestMapping("/api/article")
public class ArticleController {

    @Autowired
    ArticleService articleService;
    //添加文章
    @PostMapping
    public Result add(@RequestBody @Validated Article article){
        List<Article> a = articleService.findByTitle(article);
        //判断标题是否存在
        if (!a.isEmpty()){
            return Result.error("文章标题重复");
        }
        articleService.add(article);
        return Result.success();
    }
    //获取文章列表
    @GetMapping
    public Result<PageBean<Article>> list(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) String state
    ){
        PageBean<Article> pb = articleService.list(pageNum,pageSize,categoryId,state);
        return Result.success(pb);
    }
    //修改文章
    @PutMapping
    public Result<Article> update(@RequestBody @Validated Article article){
        if (articleService.findByUserAndIdArticleId(article) == null){
            return Result.error("你没有权限修改该文章");
        }
        articleService.update(article);
        return Result.success();
    }
    //删除文章
    @DeleteMapping
    public Result delete(Article article){
        if (articleService.findByUserAndIdArticleId(article) == null){
            return Result.error("你没有权限删除该文章");
        }
        articleService.delete(article);
        return Result.success();
    }
}
