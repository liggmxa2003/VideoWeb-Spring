package com.ligg.service.UserVideo;

import com.ligg.pojo.UserVideo;

import java.util.List;

public interface UserVideoService {
    // 添加
    void add(UserVideo userVideo);
    // 查询
    List<UserVideo> list();
    // 修改
    void update(UserVideo userVideo);
    // 删除
    void delete(UserVideo userVideo);
    //用户视频信息校验
    Boolean listByUserId(UserVideo userVideo);
    // 根据id查询
    boolean findById(UserVideo userVideo);
}
