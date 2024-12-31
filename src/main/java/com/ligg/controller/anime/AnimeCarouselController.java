package com.ligg.controller.anime;

import com.ligg.pojo.Anime;
import com.ligg.pojo.Result;
import com.ligg.service.AnimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AnimeCarouselController {

    @Autowired
    AnimeService animeService;

    //anime轮播图
    @GetMapping("/Carousel")
    public Result<List<Anime>> Carousel() {
        List<Anime> anime = animeService.Carousel();
        return Result.success(anime);
    }
}
