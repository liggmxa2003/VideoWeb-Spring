package com.ligg.mapper;

import com.ligg.pojo.Video;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface VideoMapper {
    // 查询所有视频
    @Select("select * from user_video order by user_video.update_time limit 100 ")
    List<Video> list();
}
