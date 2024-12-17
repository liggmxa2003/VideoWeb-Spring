package com.ligg.mapper.user;

import com.ligg.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    // 根据用户名查询用户
    @Select("select * from user where username=#{username}")
    User findByUserName(String username);
    // 注册
    @Insert("insert into user(username,password,email,create_time,update_time)" +
            "values (#{username},#{password},#{email},now(),now())")
    void add(User user);
    // 编辑用户信息
    @Update("update user set nickname=#{nickname},sex=#{sex},introduction=#{introduction},update_time=NOW() where id=#{id}")
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

}
