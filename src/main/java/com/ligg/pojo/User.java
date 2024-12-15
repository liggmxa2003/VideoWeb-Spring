package com.ligg.pojo;



import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.*;
import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @NotNull(groups = User.Update.class)
    private Integer id;//主键ID
    @NotEmpty
    @Pattern(regexp = "[a-z A-Z0-9]{6,16}")
    private String username;//用户名
    @JsonIgnore//忽略
    @Pattern(regexp = "^\\S{6,16}$")//2-10位非空字符
    private String password;//密码
    @NotEmpty(groups = User.Update.class)
    @Pattern(regexp = "^\\S{2,12}$")//2-10位非空字符
    private String nickname;//昵称
    @Email
    private String email;//邮箱
    @Min(value = 100000,message = "验证码长度必须是6位")
    @Max(value = 999999,message = "验证码长度必须是6位")
    private Integer code;//验证码
    private String userPic;//用户头像地址
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;//创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;//更新时间

    // 分组校验,更新组
    public interface Update extends Default {

    }
    // 分组校验,添加组
    public interface Add extends Default {

    }
}
