package com.ligg.mapper;

import com.ligg.pojo.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CategoryMapper {
    //添加分类
    @Insert("insert into category(category_name, category_alias, create_user, create_time, update_time) " +
            "values (#{categoryName},#{categoryAlias},#{createUser},NOW(),NOW())")
    void add(Category category);
    //根据分类名查询
    @Select("select * from category where category_name=#{categoryName}")
    Category findByCategoryName(String categoryName);
    //查询所有分类
    @Select("select * from category where create_user=#{userId}")
    List<Category> list(Integer userId);
    //根据id查询
    @Select("select id,category_name,category_alias,create_time,update_time from category where id=#{id}")
    Category findById(Integer id);

    //根据用户id查询
    @Select("select * from category where create_user=#{createUser}")
    Category findByCreateUser(Integer createUser);
    //更新
    @Update("update category set category_name=#{categoryName},category_alias=#{categoryAlias},update_time=NOW()" +
            " where id=#{id}")
    void update(Category category);
    //删除
    @Delete("delete from category where id=#{id}")
    void delete(Category category);
    //根据用户查询分类
    @Select("select * from category where create_user=#{createUser} and category_name=#{categoryName}")
    Category findByUserCreate(Category category);
    @Select("select * from category where create_user=#{createUser} and id=#{id}")
    Category findByUserAndIdCreate(Category category);
}
