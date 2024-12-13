package com.ligg.service.impl;

import com.ligg.mapper.UserVideoMapper;
import com.ligg.pojo.UserVideo;
import com.ligg.service.UserService;
import com.ligg.service.UserVideoService;
import com.ligg.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@Service
public class UserVideoServiceImpl implements UserVideoService {

    @Autowired
    UserVideoMapper userVideoMapper;
    // 添加用户视频
    @Override
    public void add(@RequestBody UserVideo userVideo) {
        Map<String, Object> map = ThreadLocalUtil.get();
        userVideo.setUserId((Integer) map.get("id"));
        userVideoMapper.add(userVideo);
    }
    // 查询用户视频
    @Override
    public List<UserVideo> list() {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        return userVideoMapper.list(userId);
    }
}
