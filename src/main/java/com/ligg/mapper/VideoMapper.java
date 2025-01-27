package com.ligg.mapper;

import com.ligg.pojo.Video;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface VideoMapper {
    // 查询所有视频
    List<Video> list();
    // 根据id查询视频和用户信息
    Video findById(Integer id);
    // 根据用户id查询视频
    @Select("select id,title,cover,content,video_url,create_time from video where user_id=#{userId}")
    List<Video> findVideoByUserId(Long userId);
    // 查询点赞数
    @Select("select count(id) from video_like where video_id=#{videoId}")
    Integer findVideoLikeById(Integer videoId);
    @Select("select * from video_like where video_id=#{id} and user_id=#{userId}")
    Video isUserLike(Integer id, Long userId);
}
