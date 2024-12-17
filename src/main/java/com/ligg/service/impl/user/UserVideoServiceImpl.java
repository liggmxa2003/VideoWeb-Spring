package com.ligg.service.impl.user;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ligg.mapper.user.UserVideoMapper;
import com.ligg.pojo.PageBean;
import com.ligg.pojo.Video;
import com.ligg.service.User.UserVideoService;
import com.ligg.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    public void add(@RequestBody Video video) {
        Map<String, Object> map = ThreadLocalUtil.get();
        video.setUserId((Integer) map.get("id"));
        userVideoMapper.add(video);
    }

    // 分页查询用户视频信息列表
    @Override
    public PageBean<Video> list(Integer pageNum, Integer pageSize, Integer categoryId, String state) {
        // 封装分页数据
        PageBean<Video> pb = new PageBean<>();
        //开启分页查询
        PageHelper.startPage(pageNum, pageSize);
        //获取用户id
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        List<Video> as = userVideoMapper.list(userId,categoryId,state);
        //Page提供了方法，可以获取PageHelper分页查询后得到的总记录条数和当前页数据
        Page<Video> p = (Page<Video>) as;
        //把数据填充到PageBean中
        pb.setItems(p.getResult());
        pb.setTotal(p.getTotal());
        return pb;
    }

    // 更新用户视频
    @Override
    public void update(Video userVideo) {
        userVideoMapper.update(userVideo);
    }
    // 删除用户视频
    @Override
    public void delete(Video userVideo) {
        userVideoMapper.delete(userVideo);
    }

    // 根据用户id查询用户视频
    @Override
    public Boolean listByUserId(Video userVideo) {
        Map<String, Object> map = ThreadLocalUtil.get();
        userVideo.setUserId((Integer) map.get("id"));
        Video u = userVideoMapper.findById(userVideo);
        /*if (u.getUserId() != userVideo.getUserId())
            return false;
        return true;*/
        //优化后
        //断查询出来的u数据中的userId是否和传进来的userVideo中的userId一致
        return Objects.equals(u.getUserId(), userVideo.getUserId());
    }
    // 根据id查询视频信息
    @Override
    public boolean findById(Video userVideo) {
        Video byId = userVideoMapper.findById(userVideo);
        return byId != null;
    }
}
