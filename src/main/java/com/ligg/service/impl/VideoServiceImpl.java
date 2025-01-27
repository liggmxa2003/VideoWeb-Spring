package com.ligg.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ligg.mapper.VideoMapper;
import com.ligg.pojo.Video;
import com.ligg.service.VideoService;
import com.ligg.utils.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    VideoMapper videoMapper;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    StringRedisTemplate redisTemplate;

    // 查询所有视频
    @Override
    public List<Video> list() {
        //从Redis中查询
        String video = redisTemplate.opsForValue().get("videHome");
        if (video != null) {
            try {
                return objectMapper.readValue(video, objectMapper.getTypeFactory().constructCollectionType(List.class, Video.class));
            } catch (Exception e) {
                log.error("查询视频失败: {}", e.getMessage());
            }
        }
        List<Video> list = videoMapper.list();
        try {
            redisTemplate.opsForValue().set("videHome", objectMapper.writeValueAsString(list), 20, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("查询视频失败: {}", e.getMessage());
        }
        return list;
    }

    // 根据id查询视频
    @Override
    public Video findById(Integer id) {
        return videoMapper.findById(id);
    }
    // 根据视频id查询点赞数
    @Override
    public Integer findVideoLikeById(Integer videoId) {
        return videoMapper.findVideoLikeById(videoId);
    }
}
