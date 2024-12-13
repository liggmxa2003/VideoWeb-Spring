package com.ligg.controller.user;

import com.ligg.pojo.Result;
import com.ligg.pojo.User;
import com.ligg.service.UserService;
import com.ligg.utils.JwtUtil;
import com.ligg.utils.Md5Util;
import com.ligg.utils.ThreadLocalUtil;
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
    public Result register(@Pattern(regexp = "[a-z A-Z0-9]{5,15}") String username,
                           @Pattern(regexp = "[a-z A-Z0-9]{6,15}")String password){
        //查询用户
        User user = userService.findByUsername(username);
        if (user == null){
            //用户名为空，注册用户.
            userService.register(username, password);
            return Result.success();
        }else {
            //用户名不为空,返回提示信息.
            return Result.error("用户名已存在");
        }
    }

    //登录
    @PostMapping("/login")
    public Result login(@Pattern(regexp = "[a-z A-Z0-9]{5,15}") String username,
                        @Pattern(regexp = "[a-z A-Z0-9]{5,15}")String password){
        //1、判断用户是否存在
        User loginUser = userService.findByUsername(username);
        if (loginUser == null){
            return Result.error("用户不存在");
        }
        if (Md5Util.getMD5String(password).equals(loginUser.getPassword())){
            //登录
            Map<String, Object> claims = new HashMap<>();
            claims.put("id",loginUser.getId());
            claims.put("username",loginUser.getUsername());
            String token = JwtUtil.genToken(claims);
            //把token存储到Redis中
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            operations.set(token,token,1, TimeUnit.HOURS);//1小时
            return Result.success(token);
        }
            return Result.error("密码错误");
    }
    // 获取用户信息
    @GetMapping("/userInfo")
    public Result<User> userInfo(){
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        //查询用户
        User user = userService.findByUsername(username);
        return Result.success(user);
    }

    //更新用户信息
    @PutMapping("/updateUser")
    public Result update(@RequestBody @Validated User user){
        userService.update(user);
        return Result.success();
    }

    //更新用户头像
    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl){
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

    //修改密码
    @PatchMapping("/updatePassword")
    public Result updatePassword(@RequestBody Map<String, String> params,@RequestHeader("Authorization") String token){
        //参数校验
        String oldPassword = params.get("old_pwd");
        String newPassword = params.get("new_pwd");
        String rePassword = params.get("re_pwd");
        if (!StringUtils.hasLength(oldPassword) || !StringUtils.hasLength(newPassword) || !StringUtils.hasLength(rePassword)){
            return Result.error("缺少参数");
        }
        //获取用户信息
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User loginUser = userService.findByUsername(username);
        if (!loginUser.getPassword().equals(Md5Util.getMD5String(oldPassword))){
            return Result.error("原始码错误");
        }
        if (!rePassword.equals(newPassword)){
            return Result.error("两次密码不一致");
        }
        if (loginUser.getPassword().equals(Md5Util.getMD5String(newPassword))){
            return Result.error("新密码不能与原始密码一致");
        }
        //判断密码是否小于5位，大于15位
        if (newPassword.length() < 5 || newPassword.length() > 15){
            return Result.error("密码长度需在5-15位之间");
        }
        //修改密码
        userService.updatePassword(newPassword,loginUser.getId());
        //删除token
        ValueOperations<String,String> operations = stringRedisTemplate.opsForValue();
        operations.getOperations().delete(token);
        return Result.success();
    }
}
