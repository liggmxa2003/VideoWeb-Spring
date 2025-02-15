package com.ligg.controller.user;

import com.ligg.anno.Log;
import com.ligg.dto.UserDto;
import com.ligg.pojo.Result;
import com.ligg.pojo.user.User;
import com.ligg.service.User.UserService;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Map;
import java.util.Random;
import java.util.UUID;


//用户接口
@Validated
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    StringRedisTemplate stringRedisTemplate;


    // 注册
    @PostMapping("/register")
    public Result<String> register(@Validated(User.Add.class) User user, HttpSession sessions) {
        String s = userService.register(user, sessions.getId());
        if (s == null) {
            User u = userService.findUseInfo(user);
            //生成token
            String userToken = userService.userToken(u.getUsername());
            return Result.success(userToken);
        }
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
    public Result<String> login(@RequestParam String account,
                                @Pattern(regexp = "[a-z A-Z0-9]{6,15}") String password) {

        return userService.login(account, password);
    }


    // 获取用户信息
    @GetMapping("/userInfo")
    public Result<UserDto> userInfo() {
        //查询用户
        UserDto userDto = userService.findByUsername();
        return Result.success(userDto);
    }

    //更新用户信息
    @Log
    @PutMapping("/updateUser")
    public Result<String> update(@RequestBody @Validated(User.Update.class) User user) throws QiniuException {
        String update = userService.update(user);
        if (update == null) {
            return Result.success();
        }
        return Result.error(update);
    }

    //更新用户头像
    @Log
    @PatchMapping("/updateAvatar")
    public Result<String> updateAvatar(@RequestParam @URL String avatarUrl) {
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

    //退出登录
    @DeleteMapping("/logout")
    public Result<String> logout() {
        //删除token
        userService.deleteToken();
        return Result.success();
    }

    //用户首页信息
    @GetMapping("/homeList")
    public Result<UserDto> userHomeList(@RequestParam(required = false) String username) {
        if (username == null) {
            Map<String, Object> map = ThreadLocalUtil.get();
            String user = (String) map.get("username");
            UserDto userInfoDto = userService.getUserHomeList(user);
            return Result.success(userInfoDto);
        }
        UserDto userInfoDto = userService.getUserHomeList(username);
        return Result.success(userInfoDto);
    }
}
