package com.ligg.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ligg.pojo.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OfflineMessageServiceImpl {
    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    private static final String OFFLINE_MSG_KEY = "offline_msg:";

    /**
     * 存储离线消息到Redis
     */
    public void storeOfflineMessage(String toUser, ChatMessage message) {
        try {
            String key = OFFLINE_MSG_KEY + toUser;
            String messageJson = objectMapper.writeValueAsString(message);
            redisTemplate.opsForList().rightPush(key, messageJson);
        } catch (Exception e) {
            log.error("存储离线消息失败: {}", e.getMessage());
        }
    }

    /**
     * 获取用户的所有离线消息
     */
    public List<ChatMessage> getOfflineMessages(String username) {
        List<ChatMessage> messages = new ArrayList<>();
        try {
            String key = OFFLINE_MSG_KEY + username;
            List<String> messageJsonList = redisTemplate.opsForList().range(key, 0, -1);

            if (messageJsonList != null) {
                for (String messageJson : messageJsonList) {
                    ChatMessage message = objectMapper.readValue(messageJson, ChatMessage.class);
                    messages.add(message);
                }
            }
        } catch (Exception e) {
            log.error("获取离线消息失败: {}", e.getMessage());
        }
        return messages;
    }

    /**
     * 删除用户的所有离线消息
     */
    public void deleteOfflineMessages(String username) {
        try {
            String key = OFFLINE_MSG_KEY + username;
            redisTemplate.delete(key);
        } catch (Exception e) {
            log.error("删除离线消息失败: {}", e.getMessage());
        }
    }
}