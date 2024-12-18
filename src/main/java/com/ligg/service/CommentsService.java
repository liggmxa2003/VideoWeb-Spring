package com.ligg.service;

import com.ligg.pojo.Comments;

import java.util.List;

public interface CommentsService {
    // 发布评论
    String publish(Integer id,String content);
    // 根据视频id查询评论
    List<Comments> findCommentsByVideoId(Integer id);
}
