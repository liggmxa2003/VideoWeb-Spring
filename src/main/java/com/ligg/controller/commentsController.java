package com.ligg.controller;

import com.ligg.pojo.Comments;
import com.ligg.pojo.Result;
import com.ligg.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class commentsController {

    @Autowired
    CommentsService commentsService;

    //发布评论
    @PostMapping("/publish")
    public Result<String> publish(@RequestBody Comments comments) {
        String c = commentsService.publish(comments.getId(), comments.getContent());
        if (c == null)
            return Result.success();
        return Result.error(c);
    }
    @GetMapping
    //获取视频评论列表
    public Result<List<Comments>> findCommentsByVideoId(Integer id) {
        return Result.success(commentsService.findCommentsByVideoId(id));
    }
}
