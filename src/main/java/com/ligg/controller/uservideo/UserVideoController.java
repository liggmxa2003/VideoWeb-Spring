package com.ligg.controller.uservideo;

import com.ligg.pojo.Category;
import com.ligg.pojo.Result;
import com.ligg.pojo.UserVideo;
import com.ligg.service.UserVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/video")
public class UserVideoController {

    @Autowired
    UserVideoService userVideoService;

    //发布用户视频
    @PostMapping
    public Result add(@RequestBody @Validated(UserVideo.Add.class) UserVideo userVideo){
        //发布视频
        userVideoService.add(userVideo);
        return Result.success();
    }
    //获取用户发布时视频列表
    @GetMapping
    public Result<List<UserVideo>> list(){
        return Result.success(userVideoService.list());
    }
    //编辑用户是视频
    @PutMapping
    public Result update(@RequestBody @Validated(UserVideo.Update.class) UserVideo userVideo){
        userVideoService.update(userVideo);
        return Result.success();
    }

}
