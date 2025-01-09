package com.ligg.service;

import com.ligg.pojo.AnimeEpisode;

import java.util.List;

public interface AnimeEpisodeService {
    // 根据动漫id查询动漫集
    List<AnimeEpisode> list(Long id);
}
