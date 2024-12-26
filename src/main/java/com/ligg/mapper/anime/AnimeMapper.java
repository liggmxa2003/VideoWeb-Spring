package com.ligg.mapper.anime;

import com.ligg.pojo.Anime;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AnimeMapper {

    //发布动漫
    void publishAnime(Anime anime);
    //获取轮播图
    @Select("select * from anime where recommend=#{recommend} order by id desc")
    List<Anime> Carousel(Integer recommend);
}
