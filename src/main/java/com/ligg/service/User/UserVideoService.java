package com.ligg.service.User;

import com.ligg.pojo.PageBean;
import com.ligg.pojo.Video;

public interface UserVideoService {
    // 添加
    void add(Video video);
    // 分页查询
    PageBean<Video> list(Integer pageNum, Integer pageSize, Integer categoryId, String state );
    // 修改
    void update(Video userVideo);
    // 删除
    void delete(Video userVideo);
    //用户视频信息校验
    Boolean listByUserId(Video userVideo);
    // 根据id查询
    boolean findById(Video userVideo);
}
