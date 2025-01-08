package com.ligg.pojo.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

// 用户关注
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserFollow {
    private Long id;
    private Long userId;// 用户id
    private Long followUserId;// 关注用户id
    private LocalDate createTime;// 关注时间
}
