package com.ligg.dto;

import com.ligg.pojo.Video;
import com.ligg.pojo.user.UserFollow;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private String nickname;
    private Integer sex;//性别
    private String introduction;//用户简介
    private String email;
    private String userPic;
    private List<UserFollow> Follows;//用户关注
    private List<Video> Videos;//用户视频
}
