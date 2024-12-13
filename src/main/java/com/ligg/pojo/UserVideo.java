package com.ligg.pojo;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVideo {
    private Integer id;//id
    @NotEmpty
    @Pattern(regexp = "^\\S{2,12}$")
    private String title;//标题
    @NotEmpty
    private String cover;//封面
    private Integer userId;//用户id
    @NotEmpty
    @Pattern(regexp = "^\\S{10,200}$")
    private String content;//视频介绍
    private LocalDateTime createTime;//发布时间
    private LocalDateTime updateTime;//更新时间
}
