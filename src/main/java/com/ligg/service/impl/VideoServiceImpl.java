package com.ligg.service.impl;

import com.ligg.mapper.VideoMapper;
import com.ligg.pojo.Video;
import com.ligg.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    VideoMapper videoMapper;
    // 查询所有视频
    @Override
    public List<Video> list() {
        return videoMapper.list();
    }
    // 根据id查询视频
    @Override
    public Video findById(Long id) {
        return videoMapper.findById(id);
    }
}
