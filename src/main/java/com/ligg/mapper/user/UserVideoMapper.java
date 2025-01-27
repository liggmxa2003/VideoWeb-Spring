package com.ligg.mapper.user;

import com.ligg.pojo.Video;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserVideoMapper {
    // 添加用户和视频
    @Insert("insert into video(title,cover,user_id,content,video_url,create_time,update_time)" +
            "values(#{title},#{cover},#{userId},#{content},#{videoUrl},NOW(),NOW())")
    void add(Video video);

    // 分页查询用户视频
    @Select("select * from video where user_id=#{userId} order by update_time desc")
    List<Video> list(Long userId, Integer categoryId, String state);

    // 修改用户视频信息
    void update(Video userVideo);

    // 根据用户id查询视频信息
    @Select("select title from video where user_id=#{userId}")
    List<Video> listByUserId(Video userVideo);

    // 根据视频id查询视频信息
    @Select("select * from video where id=#{id}")
    Video findById(Integer id);

    //根据id删除视频信息
    @Delete("delete from video where id=#{id}")
    void delete(Video userVideo);

    // 更新视频点赞数
    @Update("update video set likes_count=likes_count+1 where id=#{videoId}")
    void updateVideoLike(Integer videoId);

    @Insert("insert into video_likes(user_id,video_id)" +
            "values (#{userId},#{videoId})")
    void addVideoLike(Long userId, Integer videoId);

    // 查询用户是否点赞过视频
    @Select("select count(id) from video_likes where video_id=#{videoId} and user_id=#{userId}")
    int findVideoLikeById(Integer videoId, Long userId);

    @Update("update video set likes_count=likes_count-1 where id=#{videoId}")
    void updateVideoLikeMinusOne(Integer videoId);

    @Delete("delete from video_likes where user_id=#{userId} and video_id=#{videoId}")
    void deleteVideoLike(Long userId, Integer videoId);

    @Select("select * from video_likes where user_id=#{userId} and video_id=#{videoId}")
    Boolean findUserVideoLikeById(Long userId, Integer videoId);

    //更新视频收藏数
    @Update("update video set favorite_count=favorite_count+1 where id=#{videoId}")
    void updateVideoFavorite(Integer videoId);

    @Insert("insert into video_favorite(user_id,video_id,Favorite_at)" +
            "values (#{userId},#{videoId},NOW())")
    void addVideoFavorite(Long userId, Integer videoId);

    @Select("select count(id) from video_favorite where video_id=#{videoId} and user_id=#{userId}")
    int findUserVideoFavoriteById(Long userId, Integer videoId);

    @Update("update video set favorite_count=favorite_count-1 where id=#{videoId}")
    void updateVideoFavoriteMinusOne(Integer videoId);

    @Delete("delete from video_favorite where user_id=#{userId} and video_id=#{videoId}")
    void deleteVideoFavorite(Long userId, Integer videoId);
}
