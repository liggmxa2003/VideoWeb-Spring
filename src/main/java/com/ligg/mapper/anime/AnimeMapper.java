package com.ligg.mapper.anime;

import com.ligg.pojo.Anime;
import com.ligg.pojo.AnimeEpisode;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AnimeMapper {

    //发布动漫
    void publishAnime(Anime anime);
    //获取轮播图
    @Select("select * from anime where recommend=#{recommend} order by anime_id desc")
    List<Anime> Carousel(Integer recommend);
    //发布动漫章节
    @Insert("insert into anime_episode(anime_id,episode_title,duration,episode_number,episode_image,episode_video,air_date) " +
            "values(#{animeId},#{episodeTitle},#{duration},#{episodeNumber},#{episodeImage},#{episodeVideo},NOW())")
    void publishEpisode(AnimeEpisode episode);
    //获取动漫的章节
    List<AnimeEpisode> episode(Long animeId);
    //获取动漫列表
    @Select("select * from anime order by anime_id desc")
    List<Anime> list();
    //更新番剧信息
    @Update("update anime set title=#{title},cover_image=#{coverImage},status=#{status},description=#{description},release_date=#{releaseDate},updated_at=NOW() " +
            "where anime_id=#{animeId}")
    void update(Anime anime);
    //删除番剧
    @Delete("delete from anime where anime_id=#{animeId}")
    int delete(Long animeId);
    //查询番剧信息
    List<Anime> findById(Long id);
    //查询番剧章节信息
    @Select("select * from anime_episode where anime_id=#{animeId}")
    List<AnimeEpisode> findEpisodeById(Long animeId);
    @Select("select * from anime where anime_id=#{animeId}")
    Anime findAnimeById(Long animeId);
}
