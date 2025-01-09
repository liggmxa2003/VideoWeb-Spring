package com.ligg.service.User;

import com.ligg.pojo.Result;
import com.ligg.pojo.data.UserFollowData;
import com.ligg.pojo.user.UserFollow;

import java.util.List;

public interface UserFollowService {
    Result<String> follow(Long id, Boolean isFollow);
    List<UserFollow> list(Long id);
    // 关注列表
    List<UserFollowData> followList();
}
