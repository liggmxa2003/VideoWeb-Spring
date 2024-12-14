package com.ligg.mapper.user;

import com.ligg.pojo.Video;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserVideoMapper {
    // 添加用户和视频
    @Insert("insert into user_video(title,cover,user_id,content,create_time,update_time)"+
            "values(#{title},#{cover},#{userId},#{content},NOW(),NOW())")
    void add(Video userVideo);
    // 分页查询用户视频
    @Select("select * from user_video where user_id=#{userId} order by update_time desc")
    List<Video> list(Integer userId, Integer categoryId, String state);
    // 修改用户视频信息
    void update(Video userVideo);
    // 根据用户id查询视频信息
    @Select("select title from user_video where user_id=#{userId}")
    List<Video> listByUserId(Video userVideo);
    // 根据视频id查询视频信息
    @Select("select * from user_video where id=#{id}")
    Video findById(Video userVideo);
    //根据id删除视频信息
    @Delete("delete from user_video where id=#{id}")
    void delete(Video userVideo);
}
