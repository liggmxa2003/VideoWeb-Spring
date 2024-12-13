package com.ligg.pojo;


import com.ligg.anno.State;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {
    private Integer id;//主键ID
    @NotEmpty
    @Pattern(regexp = "^\\S{2,20}$")//2-10位非空字符
    private String title;//文章标题
    @Pattern(regexp = "^\\S{10,200}$")
    private String content;//文章内容
    @URL
    private String coverImg;//封面图像
    @State
    private String state;//发布状态 已发布|草稿
    private Integer categoryId;//文章分类id
    private String categoryName;//文章分类名称
    private Integer createUser;//创建人ID
    private LocalDateTime createTime;//创建时间
    private LocalDateTime updateTime;//更新时间
}
