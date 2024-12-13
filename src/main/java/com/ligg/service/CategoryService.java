package com.ligg.service;

import com.ligg.pojo.Category;

import java.util.List;

public interface CategoryService {
    //添加分类
    void add(Category category);
    //根据分类名查询
    Category findByCategoryName(String categoryName);
    //根据用户查询分类
    Category findByCategoryUser(Category category);
    //查询所有分类
    List<Category> list();
    //根据id查询
    Category findById(Integer id);
    //修改
    void update(Category category);
    //删除
    void delete(Category category);
    //用户id和分类id查询
    Category findByUserAndIdCreate(Category category);
}
