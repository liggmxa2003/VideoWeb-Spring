package com.ligg.mapper;

import com.ligg.pojo.Article;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ArticleMapper {
    //添加文章

    void add(Article article);
    //查询用户文章标题
    @Select("select * from article where title=#{title} and create_user=#{createUser}")
    List<Article> findByTitle(Article article);
    //查询文章
    List<Article> list(Integer userId, Integer categoryId, String state);
    //修改文章
    void update(Article article);
    //根据用户id和文章id查询文章
    @Select("select * from article where create_user=#{createUser} and id=#{id}")
    Article findByUserAndIdArticleId(Article article);
    //删除文章
    @Delete("delete from article where id=#{id}")
    void delete(Article article);
}
