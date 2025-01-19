package com.ligg.service.impl.user;

import com.ligg.mapper.user.UserMapper;
import com.ligg.pojo.Result;
import com.ligg.pojo.user.User;
import com.ligg.utils.JwtUtil;
import com.ligg.utils.Md5Util;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserMapper userMapper;

    @Mock
    private StringRedisTemplate stringRedisTemplate;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        // 如果需要，可以在这里进行任何通用的设置
    }

    @Test
    public void login_UserDoesNotExist_ReturnsError() {
        // 准备：模拟用户不存在的情况
        when(userMapper.findByUserNameOrEmail(any(User.class))).thenReturn(null);

        // 执行：调用 login 方法
        Result<String> result = userService.login("nonexistentuser", "password");

        // 验证：检查返回结果是否符合预期
        assertEquals("用户不存在,请先注册", result.getMessage());
    }

    @Test
    public void login_PasswordIncorrect_ReturnsError() {
        // 准备：模拟用户存在但密码错误的情况
        User user = new User();
        user.setUsername("existinguser");
        user.setPassword(Md5Util.getMD5String("correctpassword"));

        when(userMapper.findByUserNameOrEmail(any(User.class))).thenReturn(user);

        // 执行：调用 login 方法
        Result<String> result = userService.login("existinguser", "wrongpassword");

        // 验证：检查返回结果是否符合预期
        assertEquals("密码错误", result.getMessage());
    }

    @Test
    public void login_SuccessfulLogin_ReturnsToken() {
        // 准备：模拟用户存在且密码正确的情况
        User user = new User();
        user.setId(1L);
        user.setUsername("existinguser");
        user.setPassword(Md5Util.getMD5String("correctpassword"));

        when(userMapper.findByUserNameOrEmail(any(User.class))).thenReturn(user);
        when(userMapper.findByUserName("existinguser")).thenReturn(user);

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("username", user.getUsername());
        String token = JwtUtil.genToken(claims);

        ValueOperations<String, String> operations = mock(ValueOperations.class);
        when(stringRedisTemplate.opsForValue()).thenReturn(operations);

        // 执行：调用 login 方法
        Result<String> result = userService.login("existinguser", "correctpassword");

        // 验证：检查返回结果是否符合预期
        assertEquals(token, result.getData());
    }

    @Test
    public void register_UserExists_ReturnsUsernameExists() {
        User existingUser = new User();
        existingUser.setUsername("existinguser");
        when(userMapper.findByUserName("existinguser")).thenReturn(existingUser);

        User newUser = new User();
        newUser.setUsername("existinguser");

        String result = userService.register(newUser, "session123");

        assertEquals("用户名已存在", result);
    }

    @Test
    public void register_VerificationCodeNotExists_ReturnsGetVerificationCode() {
        User newUser = new User();
        newUser.setUsername("newuser");

        when(userMapper.findByUserName("newuser")).thenReturn(null);
        when(stringRedisTemplate.hasKey("emailsession123:null:false")).thenReturn(false);

        String result = userService.register(newUser, "session123");

        assertEquals("请先获取验证码", result);
    }

    @Test
    public void register_VerificationCodeMismatch_ReturnsVerificationCodeError() {
        User newUser = new User();
        newUser.setUsername("newuser");
        newUser.setCode(123456);

        when(userMapper.findByUserName("newuser")).thenReturn(null);
        when(stringRedisTemplate.hasKey("emailsession123:newuser@example.com:false")).thenReturn(true);
        when(stringRedisTemplate.opsForValue().get("emailsession123:newuser@example.com:false")).thenReturn("654321");

        String result = userService.register(newUser, "session123");

        assertEquals("验证码错误", result);
    }

    @Test
    public void register_SuccessfulRegistration_ReturnsNull() {
        User newUser = new User();
        newUser.setUsername("newuser");
        newUser.setEmail("newuser@example.com");
        newUser.setPassword("password");
        newUser.setCode(123456);

        when(userMapper.findByUserName("newuser")).thenReturn(null);
        when(stringRedisTemplate.hasKey("emailsession123:newuser@example.com:false")).thenReturn(true);
        when(stringRedisTemplate.opsForValue().get("emailsession123:newuser@example.com:false")).thenReturn("123456");

        // 添加模拟代码，确保 opsForValue() 返回一个有效的 ValueOperations 对象
        ValueOperations<String, String> operations = mock(ValueOperations.class);
        when(stringRedisTemplate.opsForValue()).thenReturn(operations);

        String result = userService.register(newUser, "session123");

        assertEquals(null, result);
    }
}
