package com.ligg.mapper;

import com.ligg.pojo.UserVideo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserVideoMapper {
    // 添加用户和视频
    @Insert("insert into user_video(title,cover,user_id,content,create_time,update_time)"+
            "values(#{title},#{cover},#{userId},#{content},NOW(),NOW())")
    void add(UserVideo userVideo);
    // 查询用户视频
    @Select("select * from user_video where user_id=#{userId}")
    List<UserVideo> list(Integer userId);
}
