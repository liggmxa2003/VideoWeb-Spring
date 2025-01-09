package com.ligg.controller.anime;

import com.ligg.pojo.AnimeEpisode;
import com.ligg.pojo.Result;
import com.ligg.service.AnimeEpisodeService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/anime/episode")
public class AnimeEpisodeController {

    @Resource
    AnimeEpisodeService animeEpisodeService;

    // 番剧剧集列表
    @GetMapping("/{animeId}")
    public Result<List<AnimeEpisode>> list(@PathVariable("animeId")Long id ) {
        return Result.success(animeEpisodeService.list(id));
    }
}
