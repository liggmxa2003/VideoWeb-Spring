package com.ligg.service;

import com.ligg.pojo.Video;

import java.util.List;

public interface VideoService {
    // 查询所有视频
    List<Video> list();
    // 根据id查询视频
    Video findById(Integer id);
    // 根据视频id查询点赞数
    Integer findVideoLikeById(Integer videoId);
}
