package com.ligg.mapper;

import com.ligg.pojo.Video;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SearchMapper {
    // 根据标题和简介搜索
    List<Video> search(String keyword);
}
