package com.ligg.controller.user;

import com.ligg.pojo.Result;
import com.ligg.pojo.User;
import com.ligg.service.UserService;
import com.ligg.utils.JwtUtil;
import com.ligg.utils.Md5Util;
import com.ligg.utils.ThreadLocalUtil;
import com.qiniu.common.QiniuException;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


//用户接口
@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    StringRedisTemplate stringRedisTemplate;


    // 注册
    @PostMapping("/register")
    public Result<String> register(@Validated(User.Add.class) User user, HttpSession sessions) {
        String s = userService.register(user, sessions.getId());
        if (s == null)
            return Result.success();
        else
            return Result.error(s);
    }

    //重置密码
    @PostMapping("/resetPassword")
    public Result<String> resetPassword(@Email String email,
                                        @Pattern(regexp = "[a-z A-Z0-9]{6,15}") String password,
                                        //验证码必须是6位
                                        @Min(value = 100000, message = "验证码长度必须是6位")
                                        @Max(value = 999999, message = "验证码长度必须是6位")
                                        Integer code,
                                        HttpSession sessions) {
        String s = userService.updatePasswordWhereEmail(email, password, code, sessions.getId());
        if (s == null)
            return Result.success();
        else
            return Result.error(s);
    }


    //登录
    @PostMapping("/login")
    public Result login(@Pattern(regexp = "[a-z A-Z0-9]{6,15}") String username,
                        @Pattern(regexp = "[a-z A-Z0-9]{6,15}") String password) {
        //校验
        String u = userService.login(username, password);
        if (u != null)
            return Result.error(u);
        //生成token
        String token = userService.userToken(username, password);
        return Result.success(token);
    }


    // 获取用户信息
    @GetMapping("/userInfo")
    public Result<User> userInfo() {
        //查询用户
        User user = userService.findByUsername();
        return Result.success(user);
    }

    //更新用户信息
    @PutMapping("/updateUser")
    public Result<String> update(@RequestBody @Validated(User.Update.class) User user) throws QiniuException {
        String update = userService.update(user);
        if (update != null) {
            return Result.error(update);
        }
        return Result.success();
    }

    //更新用户头像
    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl) {
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

    //修改密码
    @PatchMapping("/updatePassword")
    public Result<String> updatePassword(@RequestBody Map<String, String> params, @RequestHeader("Authorization") String token) {
        //参数校验
        String oldPassword = params.get("old_pwd");
        String newPassword = params.get("new_pwd");
        String rePassword = params.get("re_pwd");
        if (!StringUtils.hasLength(oldPassword) || !StringUtils.hasLength(newPassword) || !StringUtils.hasLength(rePassword)) {
            return Result.error("缺少参数");
        }
        //获取用户信息
        User loginUser = userService.findByUsername();
        if (!loginUser.getPassword().equals(Md5Util.getMD5String(oldPassword))) {
            return Result.error("原始码错误");
        }
        if (!rePassword.equals(newPassword)) {
            return Result.error("两次密码不一致");
        }
        if (loginUser.getPassword().equals(Md5Util.getMD5String(newPassword))) {
            return Result.error("新密码不能与原始密码一致");
        }
        //判断密码是否小于5位，大于15位
        if (newPassword.length() < 5 || newPassword.length() > 15) {
            return Result.error("密码长度需在5-15位之间");
        }
        //修改密码
        userService.updatePassword(newPassword, loginUser.getId());
        //删除token
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.getOperations().delete(token);
        return Result.success();
    }


}
