package com.ligg.service;

import com.ligg.pojo.ChatMessage;

import java.util.List;

public interface ChatMessageService {

    /**
     * 保存聊天消息
     * @param message 消息对象
     */
    void saveMessage(ChatMessage message);

    /**
     * 获取两个用户之间的聊天记录
     * @param user1 用户1
     * @param user2 用户2
     * @return 聊天记录列表
     */
    List<ChatMessage> getChatHistory(String user1, String user2);

    /**
     * 获取用户的未读消息数量
     * @param username 用户名
     * @return 未读消息数量
     */
    Integer getUnreadCount(String username);

    /**
     * 将消息标记为已读
     * @param fromUser 发送者
     * @param toUser 接收者
     */
    void markAsRead(String fromUser, String toUser);
}
