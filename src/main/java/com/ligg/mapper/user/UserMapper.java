package com.ligg.mapper.user;

import com.ligg.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserMapper {
    // 根据用户名查询用户
    @Select("select * from user where username=#{username}")
    User findByUserName(String username);
    // 注册
    @Insert("insert into user(username,password,nickname,email,create_time,update_time)" +
            "values (#{username},#{password},#{nickname},#{email},now(),now())")
    void add(User user);
    // 编辑用户信息
    void update(User user);
    // 更新头像
    @Update("update user set user_pic=#{avatarUrl},update_time=NOW() where id=#{id}")
    void updateAvatar(String avatarUrl,Integer id);
    //修改密码
    @Update("update user set password=#{password},update_time=now() where id=#{id}")
    void updatePassword(String password, Integer id);

    // 根据邮箱查询用户
    @Select("select * from user where email=#{email}")
    User findByUSerEmail(String email);
    // 根据邮箱修改密码
    @Update("update user set password=#{password},update_time=now() where email=#{email}")
    void updatePasswordWhereEmail(String password, String email);
    //查询用户列表
    @Select("select nickname,user_pic from user where id=#{id}")
    List<User> findByUserId(Integer id);
    @Select("select nickname,user_pic from user where username=#{username}")
    List<User> findByUserChat(String username);
    @Update("update user set id=#{uId} where id=#{id}")
    void updateUserId(Integer id, Long uId);
}
