package com.ligg.mapper;

import com.ligg.pojo.ChatMessage;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ChatMessageMapper {

    /**
     * 获取用户的未读消息数量
     * @param username 用户名
     * @return 未读消息数量
     */
    @Select("SELECT COUNT(*) FROM chat_message WHERE to_user = #{username} AND is_read = 0")
    Integer getUnreadCount(String username);

    /**
     * 获取两个用户之间的聊天记录
     * @param user1 用户1
     * @param user2 用户2
     * @return 聊天记录列表
     */
    @Select("SELECT * FROM chat_message WHERE " +
            "(from_user = #{user1} AND to_user = #{user2}) OR " +
            "(from_user = #{user2} AND to_user = #{user1}) " +
            "ORDER BY send_time ASC")
    List<ChatMessage> getChatHistory(String user1, String user2);
    /**
     * 插入新消息
     * @param message 消息对象
     * @return 影响行数
     */
    @Insert("INSERT INTO chat_message (from_user, to_user, content, send_time, is_read, created_at) " +
            "VALUES (#{fromUser}, #{toUser}, #{content}, #{sendTime}, #{isRead}, #{createdAt})")
    int insert(ChatMessage message);

    /**
     * 将消息标记为已读
     * @param fromUser 发送者
     * @param toUser 接收者
     * @return 影响行数
     */
    @Update("UPDATE chat_message SET is_read = 1 " +
            "WHERE from_user = #{fromUser} AND to_user = #{toUser} AND is_read = 0")
    int markAsRead(String fromUser, String toUser);
}
