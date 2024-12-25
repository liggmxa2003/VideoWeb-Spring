package com.ligg.controller.anime;

import com.ligg.pojo.Anime;
import com.ligg.pojo.Result;
import com.ligg.service.AnimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
