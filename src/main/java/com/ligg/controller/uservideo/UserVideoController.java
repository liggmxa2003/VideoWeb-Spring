package com.ligg.controller.uservideo;

import com.ligg.pojo.PageBean;
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

    //分页查询用户视频信息列表
    @GetMapping
    public Result<PageBean<UserVideo>> list(
            Integer pageNum,//当前页
            Integer pageSize,//每页条数
            @RequestParam(required = false) Integer categoryId,//分类id
            @RequestParam(required = false) String state //发布状态
    ){
        PageBean<UserVideo> pb = userVideoService.list(pageNum,pageSize,categoryId,state);
        return Result.success(pb);
    }
    //发布用户视频
    @PostMapping
    public Result add(@RequestBody @Validated(UserVideo.Add.class) UserVideo userVideo){
        //发布视频
        userVideoService.add(userVideo);
        return Result.success();
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
