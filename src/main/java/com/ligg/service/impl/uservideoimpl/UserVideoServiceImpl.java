package com.ligg.service.impl.uservideoimpl;

import com.ligg.mapper.UserVideoMapper;
import com.ligg.pojo.UserVideo;
import com.ligg.service.UserVideo.UserVideoService;
import com.ligg.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    // 更新用户视频
    @Override
    public void update(UserVideo userVideo) {
        userVideoMapper.update(userVideo);
    }
    // 删除用户视频
    @Override
    public void delete(UserVideo userVideo) {
        userVideoMapper.delete(userVideo);
    }

    // 根据用户id查询用户视频
    @Override
    public Boolean listByUserId(UserVideo userVideo) {
        Map<String, Object> map = ThreadLocalUtil.get();
        userVideo.setUserId((Integer) map.get("id"));
        UserVideo u = userVideoMapper.findById(userVideo);
        /*if (u.getUserId() != userVideo.getUserId())
            return false;
        return true;*/
        //优化后
        //断查询出来的u数据中的userId是否和传进来的userVideo中的userId一致
        return Objects.equals(u.getUserId(), userVideo.getUserId());
    }
    // 根据id查询视频信息
    @Override
    public boolean findById(UserVideo userVideo) {
        UserVideo byId = userVideoMapper.findById(userVideo);
        return byId != null;
    }
}
