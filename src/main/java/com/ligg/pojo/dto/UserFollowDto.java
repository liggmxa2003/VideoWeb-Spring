package com.ligg.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserFollowDto {
    private Long id;
    private Long userId;
    private Long followUserId;
    private LocalDate createTime;
    private String nickname;
    private String userPic;
}
