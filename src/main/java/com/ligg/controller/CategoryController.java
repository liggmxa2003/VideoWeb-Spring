package com.ligg.controller;

import com.ligg.pojo.Category;
import com.ligg.pojo.Result;
import com.ligg.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//分类接口
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;
    //添加分类
    @PostMapping
    public Result add(@RequestBody @Validated(Category.Add.class) Category category){
        Category c = categoryService.findByCategoryUser(category);
        if (c != null){
            return Result.error("名称重复");
        }
        categoryService.add(category);
        return Result.success();
    }
    //查询所有分类
    @GetMapping
    public Result<List<Category>> list(){
        return Result.success(categoryService.list());
    }
    //查询分类详情
    @GetMapping("/detail")
    public Result<Category> detail(Category category){
        return Result.success(categoryService.findById(category.getId()));
    }
    //更新分类
    @PutMapping
    public Result update(@RequestBody @Validated(Category.Update.class) Category category){
        if (categoryService.findByCategoryName(category.getCategoryName()) != null){
            return Result.error("名称重复");
        }
        if (categoryService.findByUserAndIdCreate(category) == null){
            return Result.error("你没有权修改除分类");
        }
        categoryService.update(category);
        return Result.success();
    }
    //删除分类
    @DeleteMapping
    public Result delete( Category category){
        if (categoryService.findByUserAndIdCreate(category) == null){
            return Result.error("你没有权限删除该分类");
        }
        categoryService.delete(category);
        return Result.success();
    }
}
