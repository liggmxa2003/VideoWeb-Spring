package com.ligg.mapper;

import com.ligg.pojo.Barrage;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BarrageMapper {
    @Insert("<script>" +
            "INSERT INTO barrage (video_id, user_id, content, color, time_point, created_at) " +
            "VALUES " +
            "<foreach collection='list' item='item' separator=','>" +
            "(#{item.videoId}, #{item.userId}, #{item.content}, " +
            "#{item.color}, #{item.timePoint}, #{item.createdAt})" +
            "</foreach>" +
            "</script>")
    int batchInsert(@Param("list") List<Barrage> barrages);
}