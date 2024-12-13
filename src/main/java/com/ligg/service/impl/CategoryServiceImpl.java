package com.ligg.service.impl;

import com.ligg.mapper.CategoryMapper;
import com.ligg.pojo.Category;
import com.ligg.service.CategoryService;
import com.ligg.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryMapper categoryMapper;
    // 添加
    @Override
    public void add(Category category) {
        //添加用户id
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        category.setCreateUser(userId);
        categoryMapper.add(category);
    }
    // 根据分类名称查询
    @Override
    public Category findByCategoryName(String categoryName) {
        return categoryMapper.findByCategoryName(categoryName);
    }
    // 根据用户查询分类
    @Override
    public Category findByCategoryUser(Category category) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        category.setCreateUser(userId);
        return categoryMapper.findByUserCreate(category);
    }

    // 查询分类
    @Override
    public List<Category> list() {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        return categoryMapper.list(userId);
    }
    // 根据id查询分类
    @Override
    public Category findById(Integer id) {
        return categoryMapper.findById(id);
    }

    // 修改
    @Override
    public void update(@Validated Category category) {
        categoryMapper.update(category);
    }
    // 删除
    @Override
    public void delete(Category category) {
        categoryMapper.delete(category);
    }

    @Override
    public Category findByUserAndIdCreate(Category category) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        category.setCreateUser(userId);
        return categoryMapper.findByUserAndIdCreate(category);
    }

}
