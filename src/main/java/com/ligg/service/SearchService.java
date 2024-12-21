package com.ligg.service;

import com.ligg.pojo.PageBean;
import com.ligg.pojo.Result;
import com.ligg.pojo.Video;

import java.util.List;

public interface SearchService {
    // 搜索视频
    PageBean<Video> search(String keyword, Integer pageNum, Integer pageSize);
}
