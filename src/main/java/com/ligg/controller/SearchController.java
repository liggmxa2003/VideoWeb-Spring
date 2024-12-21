package com.ligg.controller;

import com.ligg.pojo.PageBean;
import com.ligg.pojo.Result;
import com.ligg.pojo.Video;
import com.ligg.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    SearchService searchService;

    // 搜索视频
    @GetMapping
    public Result<PageBean<Video>> search(String keyword,
                                          Integer pageNum,//当前页
                                          Integer pageSize//每页条数
    ) {
        PageBean<Video> pb = searchService.search(keyword,pageNum,pageSize);
        return Result.success(pb);
    }
}
