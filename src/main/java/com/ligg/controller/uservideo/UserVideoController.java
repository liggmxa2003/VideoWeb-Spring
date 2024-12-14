package com.ligg.controller.uservideo;

import com.ligg.pojo.Result;
import com.ligg.pojo.UserVideo;
import com.ligg.service.UserVideo.UserVideoService;
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
        Boolean u = userVideoService.listByUserId(userVideo);
        if (!u)
            return Result.error("您没有权限编辑该视频信息");
        userVideoService.update(userVideo);
        return Result.success();
    }
    //删除视用户视频信息
    @DeleteMapping
    public Result<UserVideo> delete(UserVideo userVideo){
        if (!userVideoService.findById(userVideo))
            return Result.error("该视频已经不存在");
        Boolean u = userVideoService.listByUserId(userVideo);
        if (!u)
            return Result.error("您没有权限编辑该视频信息");
        userVideoService.delete(userVideo);
        return Result.success();
    }
}
