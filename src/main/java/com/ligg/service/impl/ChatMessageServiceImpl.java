package com.ligg.service.impl;

import com.ligg.mapper.ChatMessageMapper;
import com.ligg.pojo.ChatMessage;
import com.ligg.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 聊天消息服务实现类
 */
@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {
    private final ChatMessageMapper chatMessageMapper;
    // 保存消息
    @Override
    public void saveMessage(ChatMessage message) {
        // 设置消息初始状态
        message.setIsRead(false);
        message.setCreatedAt(LocalDateTime.now());
        int rows = chatMessageMapper.insert(message);
        if (rows <= 0) {
            throw new RuntimeException("保存消息失败");
        }
    }
    // 获取聊天记录
    @Override
    public List<ChatMessage> getChatHistory(String user1, String user2) {
        return chatMessageMapper.getChatHistory(user1, user2);
    }
    // 获取未读消息数量
    @Override
    public Integer getUnreadCount(String username) {
        return chatMessageMapper.getUnreadCount(username);
    }
    // 标记消息为已读
    @Override
    public void markAsRead(String fromUser, String toUser) {
        int rows = chatMessageMapper.markAsRead(fromUser, toUser);
        if (rows <= 0) {
            // 可以选择是否抛出异常，这里只是记录日志
            System.out.println("没有未读消息需要标记");
        }
    }
}
