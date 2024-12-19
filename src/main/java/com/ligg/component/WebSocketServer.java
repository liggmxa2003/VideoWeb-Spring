package com.ligg.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ligg.pojo.ChatMessage;
import com.ligg.pojo.MessageData;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/ws/chat/{userId}")
@Component
public class WebSocketServer {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Map<String, Session> sessions = new ConcurrentHashMap<>();
    private String userId;

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        this.userId = userId;
        sessions.put(userId, session);
        System.out.println("用户已连接: " + userId);

        // 发送连接成功消息
        sendMessage(session, createMessage("system", "连接成功"));
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        try {
            // 解析消息
            ChatMessage chatMessage = objectMapper.readValue(message, ChatMessage.class);

            // 获取接收者的会话
            Session receiverSession = sessions.get(chatMessage.getData().getChatId());
            if (receiverSession != null && receiverSession.isOpen()) {
                // 构建发送给接收者的消息
                ChatMessage responseMessage = new ChatMessage();
                responseMessage.setType("chat_message");
                MessageData data = new MessageData();
                data.setChatId(userId); // 发送者ID
                data.setContent(chatMessage.getData().getContent());
                data.setTime(chatMessage.getData().getTime());
                responseMessage.setData(data);

                // 发送消息
                sendMessage(receiverSession, responseMessage);
            } else {
                // 发送用户不在线的消息
                sendMessage(session, createMessage("error", "用户不在线"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            sendMessage(session, createMessage("error", "消息发送失败"));
        }
    }

    @OnClose
    public void onClose() {
        if (userId != null) {
            sessions.remove(userId);
            System.out.println("用户断开连接: " + userId);
        }
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.err.println("WebSocket错误: " + throwable.getMessage());
        sendMessage(session, createMessage("error", "发生错误"));
    }

    private void sendMessage(Session session, Object message) {
        try {
            session.getBasicRemote().sendText(objectMapper.writeValueAsString(message));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ChatMessage createMessage(String type, String content) {
        ChatMessage message = new ChatMessage();
        message.setType(type);
        MessageData data = new MessageData();
        data.setContent(content);
        message.setData(data);
        return message;
    }
}

