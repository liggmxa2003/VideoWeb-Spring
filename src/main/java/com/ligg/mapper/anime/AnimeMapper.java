package com.ligg.mapper.anime;

import com.ligg.pojo.Anime;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AnimeMapper {
    //发布动漫
    void publishAnime(Anime anime);
}
