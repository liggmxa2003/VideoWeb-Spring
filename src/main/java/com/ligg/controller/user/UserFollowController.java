package com.ligg.controller.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ligg.pojo.Result;
import com.ligg.pojo.user.UserFollow;
import com.ligg.service.User.UserFollowService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/follow")
public class UserFollowController {

    @Resource
    UserFollowService userFollowService;

    // 关注或取消关注
    @PutMapping("/{id}")
    public Result<String> follow(@PathVariable("id") Long id, Boolean isFollow) {
        return userFollowService.follow(id,isFollow);
    }

    // 获取关注列表
    @GetMapping("/{id}")
    public Result<List<UserFollow>> list(@PathVariable("id") Long id) {
        List<UserFollow> list = userFollowService.list(id);
        return Result.success(list);
    }
}
