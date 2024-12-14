package com.ligg.pojo;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Video {
    @NotNull(groups = Video.Update.class)
    private Integer id;//id
    @NotEmpty
    @Pattern(regexp = "^\\S{2,20}$")
    private String title;//标题
    @NotEmpty
    private String cover;//封面
    private Integer userId;//用户id
    @NotEmpty
    @Pattern(regexp = "^\\S{2,300}$")
    private String content;//视频介绍
    private LocalDateTime createTime;//发布时间
    private LocalDateTime updateTime;//更新时间

    // 分组校验,更新组
    public interface Update extends Default {

    }
    // 分组校验,添加组
    public interface Add extends Default {

    }
}
