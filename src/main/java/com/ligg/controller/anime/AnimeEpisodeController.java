package com.ligg.controller.anime;

import com.ligg.dto.AnimeDto;
import com.ligg.pojo.AnimeEpisode;
import com.ligg.pojo.Result;
import com.ligg.service.AnimeEpisodeService;
import com.ligg.service.AnimeService;
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
    @Resource
    AnimeService animeService;
//    // 番剧剧集列表
//    @GetMapping("/{animeId}")
//    public Result<List<AnimeEpisode>> list(@PathVariable("animeId")Long id ) {
//        return Result.success(animeEpisodeService.list(id));
//    }
    //番剧详细信息
    @GetMapping("/{animeId}")
    private Result<AnimeDto> findById(@PathVariable("animeId") Long id){
        AnimeDto episode = animeService.findEpisode(id);
        return Result.success(episode);
    }
}
