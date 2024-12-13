package com.ligg.controller.user;

import com.ligg.pojo.Result;
import com.ligg.pojo.UserVideo;
import com.ligg.service.UserVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserVideoController {

    @Autowired
    UserVideoService userVideoService;

    //发布用户视频
    @PostMapping("/addVideo")
    public Result add(@RequestBody UserVideo userVideo){
        //发布视频
        userVideoService.add(userVideo);
        return Result.success();
    }
    //用户发布时视频列表
    @GetMapping("/video")
    public Result<List<UserVideo>> list(){
        return Result.success(userVideoService.list());
    }

}
