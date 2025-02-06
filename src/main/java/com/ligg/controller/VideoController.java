package com.ligg.controller;

import com.ligg.pojo.Barrage;
import com.ligg.pojo.Result;
import com.ligg.pojo.Video;
import com.ligg.service.BarrageService;
import com.ligg.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/video")
public class VideoController {

    @Autowired
    BarrageService barrageService;
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
    //获取弹幕
    @GetMapping("/barrage")
    public Result<List<Barrage>> getBarrage(@RequestParam Integer videoId) {
        return Result.success(barrageService.getBarrage(videoId));
    }
}
