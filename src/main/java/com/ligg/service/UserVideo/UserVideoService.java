package com.ligg.service.UserVideo;

import com.ligg.pojo.PageBean;
import com.ligg.pojo.UserVideo;

import java.util.List;

public interface UserVideoService {
    // 添加
    void add(UserVideo userVideo);
    // 分页查询
    PageBean<UserVideo> list(Integer pageNum, Integer pageSize, Integer categoryId, String state );
    // 修改
    void update(UserVideo userVideo);
    // 删除
    void delete(UserVideo userVideo);
    //用户视频信息校验
    Boolean listByUserId(UserVideo userVideo);
    // 根据id查询
    boolean findById(UserVideo userVideo);
}
