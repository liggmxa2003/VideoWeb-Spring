package com.ligg.service.impl.user;

import com.ligg.mapper.user.UserFollowMapper;
import com.ligg.pojo.Result;
import com.ligg.pojo.user.UserFollow;
import com.ligg.utils.ThreadLocalUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserFollowServiceImplTest {

    @Mock
    private UserFollowMapper userFollowMapper;

    @InjectMocks
    private UserFollowServiceImpl userFollowService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void follow_FollowingSelf_ReturnsError() {
        Long userId = 1L;
        Map<String, Object> map = new HashMap<>();
        map.put("id", userId);
        ThreadLocalUtil.set(map);

        Result<String> result = userFollowService.follow(userId, true);

        assertEquals("不能关注自己", result.getMessage());
    }

    @Test
    public void follow_AlreadyFollowing_ReturnsError() {
        Long userId = 1L;
        Long followId = 2L;
        Map<String, Object> map = new HashMap<>();
        map.put("id", userId);
        ThreadLocalUtil.set(map);

        when(userFollowMapper.list(userId, followId)).thenReturn(Collections.singletonList(new UserFollow()));

        Result<String> result = userFollowService.follow(followId, true);

        assertEquals("已关注", result.getMessage());
    }

    @Test
    public void follow_UserDoesNotExist_ReturnsError() {
        Long userId = 1L;
        Long followId = 2L;
        Map<String, Object> map = new HashMap<>();
        map.put("id", userId);
        ThreadLocalUtil.set(map);

        when(userFollowMapper.list(userId, followId)).thenReturn(Collections.emptyList());
        when(userFollowMapper.findByUsername(followId)).thenReturn(0);

        Result<String> result = userFollowService.follow(followId, true);

        assertEquals("关注的用户不存在", result.getMessage());
    }

    @Test
    public void follow_SuccessfulFollow_ReturnsSuccess() {
        Long userId = 1L;
        Long followId = 2L;
        Map<String, Object> map = new HashMap<>();
        map.put("id", userId);
        ThreadLocalUtil.set(map);

        when(userFollowMapper.list(userId, followId)).thenReturn(Collections.emptyList());
        when(userFollowMapper.findByUsername(followId)).thenReturn(1);

        Result<String> result = userFollowService.follow(followId, true);

        assertEquals("操作成功", result.getMessage());
        verify(userFollowMapper, times(1)).userFollow(userId, followId);
    }

    @Test
    public void follow_SuccessfulUnfollow_ReturnsSuccess() {
        Long userId = 1L;
        Long followId = 2L;
        Map<String, Object> map = new HashMap<>();
        map.put("id", userId);
        ThreadLocalUtil.set(map);

        Result<String> result = userFollowService.follow(followId, false);

        assertEquals("操作成功", result.getMessage());
        verify(userFollowMapper, times(1)).userUnFollow(userId, followId);
    }

    @Test
    public void list_UserFollowMapperReturnsEmptyList_ReturnsEmptyList() {
        Long userId = 1L;
        Long id = 2L;
        Map<String, Object> map = new HashMap<>();
        map.put("id", userId);
        ThreadLocalUtil.set(map);

        when(userFollowMapper.list(userId, id)).thenReturn(Collections.emptyList());

        List<UserFollow> result = userFollowService.list(id);

        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void list_UserFollowMapperReturnsNonEmptyList_ReturnsNonEmptyList() {
        Long userId = 1L;
        Long id = 2L;
        Map<String, Object> map = new HashMap<>();
        map.put("id", userId);
        ThreadLocalUtil.set(map);

        UserFollow userFollow = new UserFollow();
        when(userFollowMapper.list(userId, id)).thenReturn(Collections.singletonList(userFollow));

        List<UserFollow> result = userFollowService.list(id);

        assertEquals(Collections.singletonList(userFollow), result);
    }
}
