package com.ligg.service.impl;

import com.ligg.mapper.CommentsMapper;
import com.ligg.pojo.Comments;
import com.ligg.service.CommentsService;
import com.ligg.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class CommentsServiceImpl implements CommentsService {

    @Autowired
    CommentsMapper commentsMapper;
    @Override
    public String publish(Integer id,String comments) {
        Map<String, Object> map = ThreadLocalUtil.get();
        if (map == null){
            return "请先登录后在发布评论";
        }
        Comments comment = new Comments();
        comment.setVideoId(id);
        comment.setUserId((Long) map.get("id"));
        comment.setContent(comments);
        commentsMapper.publish(comment);
        return null;
    }

    // 查询评论
    @Override
    public List<Comments> findCommentsByVideoId(Integer id) {
        return commentsMapper.findCommentsByVideoId(id);
    }
}
