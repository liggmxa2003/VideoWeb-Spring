package com.ligg.service.impl.anime;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ligg.dto.AnimeDto;
import com.ligg.mapper.anime.AnimeMapper;
import com.ligg.mapper.user.UserMapper;
import com.ligg.pojo.*;
import com.ligg.pojo.user.User;
import com.ligg.service.AnimeService;
import com.ligg.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class AnimeServiceImpl implements AnimeService {

    @Autowired
    AnimeMapper animeMapper;
    @Autowired
    UserMapper userMapper;

    //发布动漫
    @Override
    public String publishAnime(Anime anime) {
        //判断用户是否有权限发布动漫
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User user = userMapper.findByUserName(username);
        if (!Objects.equals(user.getRole(), "admin"))
            return "用户没有权限发布动漫";
        animeMapper.publishAnime(anime);
        return null;
    }

    @Override
    public List<Anime> Carousel() {
        Integer recommend = 1;
        return animeMapper.Carousel(recommend);
    }

    // 发布动漫集数
    @Override
    public String publishEpisode(AnimeEpisode episode) {
        //判断用户是否有权限发布动漫
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User user = userMapper.findByUserName(username);
        if (!Objects.equals(user.getRole(), "admin"))
            return "用户没有权限发布动漫";
        animeMapper.publishEpisode(episode);
        return null;
    }

    // 获取动漫集数
    @Override
    public List<AnimeEpisode> episode(Long animeId) {
        return animeMapper.episode(animeId);
    }

    // 获取动漫列表
    @Override
    public PageBean<Anime> list(Integer pageNum, Integer pageSize) {
        // 封装分页数据
        PageBean<Anime> anime = new PageBean<>();
        //开启分页查询
        PageHelper.startPage(pageNum, pageSize);
        List<Anime> list = animeMapper.list();
        //Page提供了方法，可以获取PageHelper分页查询后得到的总记录条数和当前页数据
        Page<Anime> p = (Page<Anime>) list;
        //把数据填充到PageBean中
        anime.setItems(p.getResult());
        anime.setTotal(p.getTotal());
        return anime;
    }

    @Override
    public void update(Long AnimeId, Anime anime) {
        anime.setAnimeId(AnimeId);
        animeMapper.update(anime);
    }

    @Override
    public Result<String> delete(Long animeId) {
        animeMapper.delete(animeId);
        return Result.success();
    }

    // 根据id查询动漫
    @Override
    public List<Anime> findById(Long id) {
        return animeMapper.findById(id);
    }
    // 根据id查询动漫剧集
    @Override
    public AnimeDto findEpisode(Long animeId) {
        List<AnimeEpisode> animeEpisodes = animeMapper.findEpisodeById(animeId);

        // 初始化 DTO 对象
        AnimeDto animeDto = new AnimeDto();
        for (AnimeEpisode ignored : animeEpisodes) {
            animeDto.setEpisodes(animeEpisodes);
        }
        Anime anime = animeMapper.findAnimeById(animeId);
        animeDto.setTitle(anime.getTitle());
        animeDto.setCoverImage(anime.getCoverImage());
        animeDto.setDescription(anime.getDescription());
        animeDto.setReleaseDate(anime.getReleaseDate());
        animeDto.setStatus(anime.getStatus());
        return animeDto;
    }
}
