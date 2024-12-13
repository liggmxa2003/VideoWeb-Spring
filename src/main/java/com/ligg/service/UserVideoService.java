package com.ligg.service;

import com.ligg.pojo.UserVideo;

import java.util.List;

public interface UserVideoService {
    // 添加
    void add(UserVideo userVideo);
    // 查询
    List<UserVideo> list();
}
