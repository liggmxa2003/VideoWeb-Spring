package com.ligg.service;

import com.ligg.pojo.Anime;

import java.util.List;

public interface AnimeService {
    //发布动漫
    String publishAnime(Anime anime);
    //轮播图
    List<Anime> Carousel();
}
