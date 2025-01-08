package com.ligg.controller;

import com.ligg.pojo.Result;
import com.ligg.pojo.Video;
import com.ligg.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/video")
public class VideoController {

    @Autowired
    VideoService videoService;
    // 查询所有视频
    @GetMapping
    public Result<List<Video>> list() {
        List<Video> list = videoService.list();
        return Result.success(list);
    }
    //获取视频详细信息
    @GetMapping("/videoInfo")
    public Result<Video> videoInfo(Integer id){
        Video video = videoService.findById(id);
        return Result.success(video);
    }
}
