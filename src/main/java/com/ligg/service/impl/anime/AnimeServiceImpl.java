package com.ligg.service.impl.anime;

import com.ligg.mapper.anime.AnimeMapper;
import com.ligg.mapper.user.UserMapper;
import com.ligg.pojo.Anime;
import com.ligg.pojo.User;
import com.ligg.service.AnimeService;
import com.ligg.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
public class AnimeServiceImpl implements AnimeService {

    @Autowired
    AnimeMapper animeMapper;
    @Autowired
    UserMapper userMapper;

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
}
