package com.ligg.controller.anime;

import com.ligg.pojo.Anime;
import com.ligg.pojo.Result;
import com.ligg.service.AnimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/anime")
public class AnimeController {

    @Autowired
    AnimeService animeService;
    //发布动漫
    @PostMapping
    public Result<String> publishAnime(@RequestBody Anime anime) {
        String a = animeService.publishAnime(anime);
        if (a != null)
            return Result.error(a);
        return Result.success();
    }

    //anime轮播图
    @GetMapping("/Carousel")
    public Result<List<Anime>> Carousel() {
        List<Anime> anime = animeService.Carousel();
        return Result.success(anime);
    }
}
