package com.ligg.service;

import com.ligg.pojo.User;
import com.qiniu.common.QiniuException;

import java.util.List;
import java.util.Map;


public interface UserService {
    // 根据用户名查询用户
    User findByUsername();
    User findUseInfo(User user);
    // 注册用户
    String register(User user,String sessionId);
    // 更新用户信息
    String update(User user) throws QiniuException;
    // 更新用户头像
    void updateAvatar(String avatarUrl);
    // 重置密码
    void updatePassword(String newPassword, Integer id);
    // 发送验证码
    String sendValidateCode(String email,String sessionId,boolean hasUsername);
    // 重置密码
    String updatePasswordWhereEmail(String email,String password,Integer code,String sessionId);
    // 登录
    String login(String username, String password);
    //token
    String userToken(String username, String password);
    // 根据用户username获取聊天对象
    List<User> findByUserChat(String username);
    // 根据账号获取用户名
    String findUsername(String username);
    // 删除token
    void deleteToken();
}
