package com.ligg.service.impl.user;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ligg.mapper.VideoMapper;
import com.ligg.mapper.user.UserVideoMapper;
import com.ligg.pojo.PageBean;
import com.ligg.pojo.Result;
import com.ligg.pojo.Video;
import com.ligg.service.User.UserVideoService;
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
    @Autowired
    VideoMapper videoMapper;

    // 添加用户视频
    @Override
    public void add(@RequestBody Video video) {
        Map<String, Object> map = ThreadLocalUtil.get();
        video.setUserId((Long) map.get("id"));
        userVideoMapper.add(video);
    }

    // 分页查询用户视频信息列表
    @Override
    public PageBean<Video> list(Integer pageNum, Integer pageSize, Integer categoryId, String state) {
        // 初始化分页对象
        PageBean<Video> pb = new PageBean<>();
        // 开始分页
        PageHelper.startPage(pageNum, pageSize);
        // 获取用户ID
        Map<String, Object> map = ThreadLocalUtil.get();
        Object idObj = map.get("id");
        long userId;
        if (idObj instanceof Integer) {
            userId = ((Integer) idObj).longValue();
        } else if (idObj instanceof Long) {
            userId = (Long) idObj;
        } else {
            throw new IllegalArgumentException("用户 ID 的类型无效： " + idObj.getClass().getName());
        }
        // 查询视频列表
        List<Video> as = userVideoMapper.list(userId, categoryId, state);
        // 将查询结果封装到PageBean中
        Page<Video> p = (Page<Video>) as;
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
        userVideo.setUserId((Long) map.get("id"));
        Video u = userVideoMapper.findById(userVideo.getId());
        /*if (u.getUserId() != userVideo.getUserId())
            return false;
        return true;*/
        //优化后
        //断查询出来的u数据中的userId是否和传进来的userVideo中的userId一致
        return Objects.equals(u.getUserId(), userVideo.getUserId());
    }

    // 根据id查询视频信息
    @Override
    public boolean findById(Integer id) {
        Video byId = userVideoMapper.findById(id);
        return byId != null;
    }

    /**
     * @param videoId 视频id
     * @param action  点赞、收藏
     */
    @Override
    public Result<String> saveAction(Integer videoId, String action) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Long userId = (Long) map.get("id");
        switch (action) {
            case "like":
                if (userVideoMapper.findVideoLikeById(videoId, userId) == 0) {
                    userVideoMapper.updateVideoLike(videoId);
                    userVideoMapper.addVideoLike(userId, videoId);
                    return Result.success("点赞成功");
                } else {
                    // 取消点赞
                    userVideoMapper.updateVideoLikeMinusOne(videoId);
                    userVideoMapper.deleteVideoLike(userId, videoId);
                    return Result.success("取消点赞");
                }
                //收藏
            case "favorite":
                if (userVideoMapper.findUserVideoFavoriteById(userId, videoId) == 0) {
                    userVideoMapper.updateVideoFavorite(videoId);
                    userVideoMapper.addVideoFavorite(userId, videoId);
                    return Result.success("收藏成功");
                } else {
                    userVideoMapper.updateVideoFavoriteMinusOne(videoId);
                    userVideoMapper.deleteVideoFavorite(userId, videoId);
                    return Result.success("取消收藏");
                }
        }
        return Result.error("操作失败");
    }

    @Override
    public Boolean findUserVideoLikeById(Integer videoId, String action) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Long userId = (Long) map.get("id");
        return switch (action) {
            case "like" -> {
                Boolean userVideoLikeById = userVideoMapper.findUserVideoLikeById(userId, videoId);
                yield Objects.requireNonNullElse(userVideoLikeById, false);
            }
            case "favorite" -> {
                int userVideoFavoriteById = userVideoMapper.findUserVideoFavoriteById(userId, videoId);
                yield userVideoFavoriteById != 0;
            }
            default -> false;
        };
    }
}
