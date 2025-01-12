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
    Video findById(Long id);
    @Select("select id,title,cover,content,video_url,create_time from video where user_id=#{userId}")
    List<Video> findVideoByUserId(Long userId);
}
