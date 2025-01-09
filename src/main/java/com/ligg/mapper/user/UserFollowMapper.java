package com.ligg.mapper.user;

import com.ligg.pojo.dto.UserFollowData;
import com.ligg.pojo.user.UserFollow;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserFollowMapper {
    // 关注
    @Insert("insert into user_follow(user_id, follow_user_id,create_time) values(#{userId}, #{id},NOW())")
    void userFollow(Integer userId, Long id);
    // 取消关注
    @Delete("delete from user_follow where user_id = #{userId} and follow_user_id = #{id}")
    void userUnFollow(Integer userId, Long id);

    // 查询关注信息
    @Select("select * from user_follow where user_id = #{userId} and follow_user_id = #{id}")
    List<UserFollow> list(Integer userId, Long id);
    // 查询关注列表
    List<UserFollowData> followList(Integer id);
}
