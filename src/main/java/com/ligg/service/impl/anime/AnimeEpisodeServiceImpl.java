package com.ligg.service.impl.anime;

import com.ligg.mapper.anime.AnimeEpisodeMapper;
import com.ligg.pojo.AnimeEpisode;
import com.ligg.service.AnimeEpisodeService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimeEpisodeServiceImpl implements AnimeEpisodeService {

    @Resource
    AnimeEpisodeMapper animeEpisodeMapper;

    @Override
    public List<AnimeEpisode> list(Long id) {
        return animeEpisodeMapper.list(id);
    }
}
