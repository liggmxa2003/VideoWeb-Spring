package com.ligg.controller.anime;

import com.ligg.pojo.Anime;
import com.ligg.pojo.Episode;
import com.ligg.pojo.PageBean;
import com.ligg.pojo.Result;
import com.ligg.service.AnimeService;
import org.apache.ibatis.annotations.Delete;
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
    //获取番剧列表
    @GetMapping
    public Result<PageBean<Anime>> list(
            Integer pageNum,//当前页
            Integer pageSize//每页条数
    ) {
        PageBean<Anime> anime = animeService.list(pageNum,pageSize);
        return Result.success(anime);
    }
    //番剧编辑
    @PutMapping("/{animeId}")
    public Result<String> update(@PathVariable("animeId")Long animeId, @RequestBody Anime anime){
        animeService.update(animeId,anime);
        return Result.success();
    }
    //番剧删除
    @DeleteMapping("/{animeId}")
    public Result<String> delete(@PathVariable("animeId")Long animeId){
        return animeService.delete(animeId);
    }
    //发布动漫集数
    @PostMapping("/episode")
    public Result<String> publishEpisode(@RequestBody Episode episode){
        String a = animeService.publishEpisode(episode);
        if (a != null)
            return Result.error(a);
        return Result.success();
    }
    //查询动漫集数
    @GetMapping("/episode")
    public Result<List<Episode>> episode(@RequestParam("animeId") Long animeId){
        List<Episode> episode = animeService.episode(animeId);
        return Result.success(episode);
    }
}
