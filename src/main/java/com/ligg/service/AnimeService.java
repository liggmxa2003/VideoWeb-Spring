package com.ligg.service;

import com.ligg.pojo.Anime;
import com.ligg.pojo.Episode;
import com.ligg.pojo.PageBean;

import java.util.List;

public interface AnimeService {
    //发布动漫
    String publishAnime(Anime anime);
    //轮播图
    List<Anime> Carousel();
    //发布动漫集
    String publishEpisode(Episode episode);
    //查询动漫集
    List<Episode> episode(Long animeId);
    //番剧列表
    PageBean<Anime> list(Integer pageNum, Integer pageSize);
    //编辑番剧
    void update(Anime anime);
}
