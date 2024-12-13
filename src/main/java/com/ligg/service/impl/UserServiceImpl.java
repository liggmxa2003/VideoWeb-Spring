package com.ligg.service.impl;

import com.ligg.mapper.UserMapper;
import com.ligg.pojo.User;
import com.ligg.service.UserService;
import com.ligg.utils.Md5Util;
import com.ligg.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    //根据查询用户名查询用户
    @Override
    public User findByUsername(String username) {
        return userMapper.findByUserName(username);
    }
    //注册用户
    @Override
    public void register(String username, String password) {
        //MD5加密
        String md5String = Md5Util.getMD5String(password);
        //注册用户
        userMapper.add(username, md5String);
    }
    //修改用户信息
    @Override
    public void update(User user) {
        Map<String,Object> map = ThreadLocalUtil.get();
        user.setId((Integer) map.get("id"));
        userMapper.update(user);
    }
    //修改用户头像
    @Override
    public void updateAvatar(String avatarUrl) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        userMapper.updateAvatar(avatarUrl, id);
    }
    //修改用户密码
    @Override
    public void updatePassword(String newPassword, Integer id) {
        userMapper.updatePassword(Md5Util.getMD5String(newPassword), id);
    }

}
