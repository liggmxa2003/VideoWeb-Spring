package com.ligg.service;

import com.ligg.pojo.Anime;
import com.ligg.pojo.AnimeEpisode;
import com.ligg.pojo.PageBean;
import com.ligg.pojo.Result;

import java.util.List;

public interface AnimeService {
    //发布动漫
    String publishAnime(Anime anime);
    //轮播图
    List<Anime> Carousel();
    //发布动漫集
    String publishEpisode(AnimeEpisode episode);
    //查询动漫集
    List<AnimeEpisode> episode(Long animeId);
    //番剧列表
    PageBean<Anime> list(Integer pageNum, Integer pageSize);
    //编辑番剧
    void update(Long AnimeId,Anime anime);
    //删除番剧
    Result<String> delete(Long animeId);
    //查询番剧详细信息
    List<Anime> findById(Long id);
}
