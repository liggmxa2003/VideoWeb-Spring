package com.ligg.service.impl.user;

import com.ligg.mapper.user.UserFollowMapper;
import com.ligg.pojo.Result;
import com.ligg.pojo.dto.UserFollowDto;
import com.ligg.pojo.user.UserFollow;
import com.ligg.service.User.UserFollowService;
import com.ligg.utils.ThreadLocalUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserFollowServiceImpl implements UserFollowService {

    @Resource
    UserFollowMapper userFollowMapper;

    // 关注和取消关注
    @Override
    public Result<String> follow(Long id, Boolean isFollow) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        if (isFollow) {
            //关注
            if (!userFollowMapper.list(userId, id).isEmpty()) {
                return Result.error("已关注");
            }
            userFollowMapper.userFollow(userId,id);
        } else {
            //取消关注
            userFollowMapper.userUnFollow(userId,id);
        }
        return Result.success();
    }

    // 查询用户关注信息
    @Override
    public List<UserFollow> list(Long id) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        return userFollowMapper.list(userId, id);
    }
    // 查询用户关注列表
    @Override
    public List<UserFollowDto> followList() {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        return userFollowMapper.followList(id);
    }
}
