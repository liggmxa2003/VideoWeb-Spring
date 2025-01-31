package com.ligg.service.impl.user;

import com.ligg.mapper.user.UserFollowMapper;
import com.ligg.pojo.Result;
import com.ligg.pojo.user.UserFollowData;
import com.ligg.pojo.user.UserFollow;
import com.ligg.service.User.UserFollowService;
import com.ligg.utils.ThreadLocalUtil;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class UserFollowServiceImpl implements UserFollowService {

    @Resource
    UserFollowMapper userFollowMapper;

    // 关注和取消关注
    @Override
    public Result<String> follow(Long id, Boolean isFollow) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Long userId = (Long) map.get("id");
        if (userId.longValue() == id){
            return Result.error("不能关注自己");
        }
        if (isFollow) {
            //关注
            if (!userFollowMapper.list(userId, id).isEmpty()) {
                return Result.error("已关注");
            }
            if (userFollowMapper.findByUsername(id)==0)
                return Result.error("关注的用户不存在");
            userFollowMapper.userFollow(userId, id);
        } else {
            //取消关注
            userFollowMapper.userUnFollow(userId, id);
        }
        return Result.success();
    }

    // 查询用户关注信息
    @Override
    public List<UserFollow> list(Long id) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Long userId = (Long) map.get("id");
        return userFollowMapper.list(userId, id);
    }

    // 查询用户关注列表
    @Override
    public List<UserFollowData> followList() {
        Map<String, Object> map = ThreadLocalUtil.get();
        Long id = (Long) map.get("id");
        return userFollowMapper.getUserFollow(id);
    }
}
