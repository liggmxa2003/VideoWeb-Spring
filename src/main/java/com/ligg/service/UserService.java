package com.ligg.service;

import com.ligg.pojo.User;

import java.util.Map;


public interface UserService {
    // 根据用户名查询用户
    User findByUsername(String username);
    // 注册用户
    String register(User user,String sessionId);
    // 更新用户信息
    void update(User user);
    // 更新用户头像
    void updateAvatar(String avatarUrl);
    // 重置密码
    void updatePassword(String newPassword, Integer id);
    // 发送验证码
    String sendValidateCode(String email,String sessionId);
}
