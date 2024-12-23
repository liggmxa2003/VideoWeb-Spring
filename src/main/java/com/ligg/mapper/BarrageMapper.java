package com.ligg.mapper;
import com.ligg.pojo.Barrage;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BarrageMapper {

    // 发送弹幕
    @Insert("insert into barrage(video_id,user_id,content,color,time_point,create_time)" +
            " values(#{videoId},#{userId},#{content},#{color},#{timePoint},NOW())")
    void sendBarrage(Barrage barrage);
}