package com.ligg.service.impl;

import com.ligg.mapper.user.UserMapper;
import com.ligg.pojo.User;
import com.ligg.service.UserService;
import com.ligg.utils.Md5Util;
import com.ligg.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    MailSender mailSender;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Value("${spring.mail.username}")
    String from;

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
        Map<String, Object> map = ThreadLocalUtil.get();
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

    /**
     * 1.生成验证码
     * 2.发送验证码到指定邮箱
     * 3.把邮箱和验证码保存到Redis中，设置过期时间3分钟
     * 4.如果发送失败把把Redis中邮箱和验证码删除
     * 5.用户注册时，再把Redis里的键值对取出对比
     */
    //发送验证码
    @Override
    public boolean sendValidateCode(String email,String sessionId) {
        String key = "email" + sessionId + ":" +email;
        if (Boolean.TRUE.equals(stringRedisTemplate.hasKey(key))){
            Long expire = Optional.ofNullable(stringRedisTemplate.getExpire(key,TimeUnit.SECONDS)).orElse(0L);
            if (expire > 120)
                return false;
        }
        //生成验证码
        Random random = new Random();
        int code = random.nextInt(899999) + 100000;

        //创建邮件内容
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(email);
        message.setSubject("用户注册");
        message.setText("验证码：" + code);
        try {
            //发送验证码
            mailSender.send(message);
            //邮箱和验证码保存到Redis中
            stringRedisTemplate.opsForValue().set(key,String.valueOf(code),3, TimeUnit.MINUTES);
            return true;
        } catch (MailException e) {
            e.printStackTrace();
            return false;
        }
    }

}
