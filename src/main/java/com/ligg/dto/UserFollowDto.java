package com.ligg.dto;

import com.ligg.pojo.Video;
import com.ligg.pojo.user.UserFollow;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFollowDto {
    private Long id;
    private Long followUserId;
    private LocalDateTime createTime;
}

