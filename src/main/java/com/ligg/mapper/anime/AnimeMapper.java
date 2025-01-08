package com.ligg.mapper.anime;

import com.ligg.pojo.Anime;
import com.ligg.pojo.Episode;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AnimeMapper {

    //发布动漫
    void publishAnime(Anime anime);
    //获取轮播图
    @Select("select * from anime where recommend=#{recommend} order by anime_id desc")
    List<Anime> Carousel(Integer recommend);
    @Insert("insert into anime_episode(anime_id,title,air_date,duration,episode_number,anime_url) " +
            "values(#{animeId},#{title},#{airDate},#{duration},#{episodeNumber},#{animeUrl})")
    void publishEpisode(Episode episode);
    //获取动漫的章节
    List<Episode> episode(Long animeId); 
    //获取动漫列表
    @Select("select * from anime order by anime_id desc")
    List<Anime> list();
    //更新番剧信息
    @Update("update anime set title=#{title},cover_image=#{coverImage},status=#{status},description=#{description},updated_at=NOW() " +
            "where anime_id=#{animeId}")
    void update(Anime anime);
    //删除番剧
    @Delete("delete from anime where anime_id=#{animeId}")
    int delete(Long animeId);
}
