package com.ligg.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comments {
    private Integer id;//
    private Integer videoId;//视频id
    private Integer userId;//用户id
    private String userPic;//用户头像
    private String nickname;//用户昵称
    private String content;//评论内容
    private LocalDateTime createTime;//评论时间
    private LocalDateTime updateTime;//修改时间
    private Long parentId;//父评论id
    private Integer status;//状态
    private Integer likeCount;//点赞数
}
