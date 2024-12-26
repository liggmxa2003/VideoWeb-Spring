package com.ligg.mapper;
import com.ligg.pojo.Barrage;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BarrageMapper {

    // 发送弹幕
    @Insert("insert into barrage(video_id,user_id,content,color,time_point,create_time)" +
            " values(#{videoId},#{userId},#{content},#{color},#{timePoint},NOW())")
    void sendBarrage(Barrage barrage);
    // 获取弹幕
    @Select("select id,user_id,video_id,content,color,time_point,create_time from barrage where video_id=#{videoId}")
    List<Barrage> getBarrage(Integer videoId);
}