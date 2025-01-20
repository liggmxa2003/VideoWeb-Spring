package com.ligg.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoLike {
    private Integer id;
    private Long userId;
    private Integer videoId;
    private LocalDateTime likedAt;//点赞时间
}
