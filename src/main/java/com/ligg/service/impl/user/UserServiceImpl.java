package com.ligg.service.impl.user;

import com.ligg.mapper.user.UserMapper;
import com.ligg.pojo.User;
import com.ligg.service.UserService;
import com.ligg.utils.JwtUtil;
import com.ligg.utils.Md5Util;
import com.ligg.utils.QiNiuOssUtil;
import com.ligg.utils.ThreadLocalUtil;
import com.qiniu.common.QiniuException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.ligg.utils.QiNiuOssUtil.parseKeyFromUrl;

@Slf4j
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
    public User findByUsername() {
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        return userMapper.findByUserName(username);
    }

    //注册用户
    @Override
    public String register(User user, String sessionId) {
        User u = userMapper.findByUserName(user.getUsername());
        if (u != null)
            return "用户名已存在";
        String key = "email" + sessionId + ":" + user.getEmail() + ":false";
        if (Boolean.TRUE.equals(stringRedisTemplate.hasKey(key))) {
            String s = stringRedisTemplate.opsForValue().get(key);//获取Redis中的验证码
            /**
             * 注意： user.getCode() 返回的是一个 Integer 类型的对象，而 s 是一个 String 类型。
             * Integer 和 String 之间不能直接进行 equals 比较。
             * 需要将将 user.getCode() 转换为 String 类型，然后再进行比较
             */
            //较验证码
            if (/*user.getCode().equals(s)*/String.valueOf(user.getCode()).equals(s)) {
                //MD5加密
                String md5String = Md5Util.getMD5String(user.getPassword());
                user.setPassword(md5String);
                //注册用户
                userMapper.add(user);
                //清除Redis中的键值对
                stringRedisTemplate.delete(key);
                return null;
            } else {
                return "验证码错误";
            }
        } else {
            return "请先获取验证码";
        }
    }

    //重置密码
    @Override
    public String updatePasswordWhereEmail(String email, String password, Integer code, String sessionId) {
        String key = "email" + sessionId + ":" + email + ":true";
        if (Boolean.TRUE.equals(stringRedisTemplate.hasKey(key))) {
            String s = stringRedisTemplate.opsForValue().get(key);
            if (String.valueOf(code).equals(s)) {
                //判断密码是否重复
                User user = userMapper.findByUSerEmail(email);
                String md5String = Md5Util.getMD5String(password);
                if (user.getPassword().equals(md5String)) {
                    return "新密码不能与原始密码一致";
                } else
                    userMapper.updatePasswordWhereEmail(md5String, email);
                //清除Redis中的键值对
                stringRedisTemplate.delete(key);
                return null;
            } else {
                return "验证码错误";
            }
        } else
            return "先获取验证码";
    }

    //登录
    @Override
    public String login(String username, String password) {
        User user = userMapper.findByUserName(username);
        if (user == null)
            return "用户不存在";
        if (!Md5Util.getMD5String(password).equals(user.getPassword()))
            return "密码错误";
        return null;
    }

    //生成token
    @Override
    public String userToken(String username, String password) {
        User user = userMapper.findByUserName(username);
        Map<String, Object> claims = new HashMap<>();//存储用户信息
        claims.put("id", user.getId());
        claims.put("username", user.getUsername());
        String token = JwtUtil.genToken(claims);

        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.set(token, token, 3, TimeUnit.HOURS);//3小时
        return token;
    }

    //根据用户username获取聊天对象
    @Override
    public List<User> findByUserChat(String username) {
        return userMapper.findByUserChat(username);
    }


    @Override
    public String findUsername(String username) {
        Map<String,Object> map = ThreadLocalUtil.get();
        String u = (String) map.get("username");
        if (username.equals(u))
            return "不可以给自己发私信";
        return null;
    }
    //删除token
   @Override
public void deleteToken() {
    // 获取 ThreadLocal 中的用户信息
    Map<String, Object> map = ThreadLocalUtil.get();
    if (map == null || !map.containsKey("username")) {
        log.warn("User information not found in ThreadLocal");
        return;
    }

    String username = (String) map.get("username");

    try {
        // 从 Redis 中获取用户的旧 token
        String oldToken = stringRedisTemplate.opsForValue().get(username);
        if (oldToken != null) {
            // 删除 Redis 中的 token
            stringRedisTemplate.delete(oldToken);
            log.info("Deleted token for user: {}", username);
        } else {
            log.warn("No token found for user: {}", username);
        }
    } catch (Exception e) {
        log.error("Error deleting token for user: {}", username, e);
    }
}



    // 修改用户信息
@Override
public String update(User user) throws QiniuException {
    Map<String, Object> map = ThreadLocalUtil.get();

    // 空指针检查
    if (map == null || map.get("id") == null || map.get("username") == null) {
        throw new IllegalArgumentException("Required parameters are missing in ThreadLocalUtil");
    }

    user.setId((Integer) map.get("id"));
    user.setUsername((String) map.get("username"));

    try {
        User oldAvatarUrl = userMapper.findByUserName(user.getUsername());

        // 如果新头像和旧头像不一样，并且旧头像不为空，就删除旧头像
        if (oldAvatarUrl != null && !Objects.equals(oldAvatarUrl.getUserPic(), user.getUserPic())) {
            if (oldAvatarUrl.getUserPic() != null && !oldAvatarUrl.getUserPic().isEmpty()) {
                String oldAvatarKey = parseKeyFromUrl(oldAvatarUrl.getUserPic());
                try {
                    QiNiuOssUtil.deleteFile(oldAvatarKey);
                } catch (Exception ignored) {
                }
            }
        }

        userMapper.update(user);
    } catch (Exception e) {
        // 处理数据库查询异常
        throw new QiniuException(e, "Failed to update user or find old avatar");
    }

    return null;
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
    public String sendValidateCode(String email, String sessionId, boolean hasUsername) {
        String key = "email" + sessionId + ":" + email + ":" + hasUsername;
        if (Boolean.TRUE.equals(stringRedisTemplate.hasKey(key))) {
            Long expire = Optional.ofNullable(stringRedisTemplate.getExpire(key, TimeUnit.SECONDS)).orElse(0L);
            if (expire > 120)
                return "邮件发送频繁，请三分钟后在重新发送";
        }
        User user = userMapper.findByUSerEmail(email);
        if (hasUsername && user == null) return "用户邮箱不存在";
        if (!hasUsername && user != null) return "邮箱已被注册";
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
            stringRedisTemplate.opsForValue().set(key, String.valueOf(code), 3, TimeUnit.MINUTES);
            return null;
        } catch (MailException e) {
            e.printStackTrace();
            return "验证码获取失败，请检查邮箱是否正确";
        }
    }

}
