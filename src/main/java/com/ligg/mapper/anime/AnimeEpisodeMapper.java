package com.ligg.mapper.anime;

import com.ligg.pojo.AnimeEpisode;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AnimeEpisodeMapper {

    @Select("select * from anime_episode where anime_id = #{id}")
    List<AnimeEpisode> list(Long id);
}
