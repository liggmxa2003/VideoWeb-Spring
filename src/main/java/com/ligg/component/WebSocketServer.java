package com.ligg.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/ws/chat")
@Component
public class WebSocketServer {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Map<String, Session> sessions = new ConcurrentHashMap<>();
    private String username;

    @OnOpen
    public void onOpen(Session session) {
        try {
            // 从 URL 参数中获取用户名
            Map<String, String> params = parseQueryString(session.getQueryString());
            this.username = params.get("username");

            if (this.username == null || this.username.isEmpty()) {
                throw new IllegalArgumentException("username is required");
            }

            // 存储会话
            sessions.put(this.username, session);
            System.out.println("用户连接成功: " + this.username);
        } catch (Exception e) {
            System.err.println("连接失败: " + e.getMessage());
            try {
                session.close();
            } catch (Exception ignored) {}
        }
    }

    @OnMessage
    public void onMessage(String messageStr, Session session) {
        try {
            if (this.username == null) {
                System.err.println("用户未认证");
                return;
            }

            // 解析消息
            Message message = objectMapper.readValue(messageStr, Message.class);

            if ("chat".equals(message.getType()) && message.getData() != null) {
                String to = message.getData().getTo();

                // 检查接收者是否存在
                if (to == null || to.isEmpty()) {
                    System.err.println("接收者不能为空");
                    return;
                }

                // 构建响应消息
                Message responseMessage = new Message();
                responseMessage.setType("chat");

                MessageData responseData = new MessageData();
                responseData.setFrom(this.username);
                responseData.setContent(message.getData().getContent());
                responseData.setTime(message.getData().getTime());
                responseMessage.setData(responseData);

                // 发送消息给接收者
                Session receiverSession = sessions.get(to);
                if (receiverSession != null && receiverSession.isOpen()) {
                    String responseStr = objectMapper.writeValueAsString(responseMessage);
                    receiverSession.getBasicRemote().sendText(responseStr);
                    System.out.println("消息发送成功: " + responseStr);
                } else {
                    System.out.println("接收者不在线: " + to);
                }
            }
        } catch (Exception e) {
            System.err.println("处理消息失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @OnClose
    public void onClose(Session session) {
        if (this.username != null) {
            sessions.remove(this.username);
            System.out.println("用户断开连接: " + this.username);
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        System.err.println("用户 " + this.username + " 发生错误: " + error.getMessage());
    }

    private Map<String, String> parseQueryString(String queryString) {
        Map<String, String> params = new ConcurrentHashMap<>();
        if (queryString != null && !queryString.isEmpty()) {
            for (String param : queryString.split("&")) {
                String[] parts = param.split("=", 2);
                if (parts.length == 2) {
                    params.put(parts[0], parts[1]);
                }
            }
        }
        return params;
    }
}

// 消息类
class Message {
    private String type;
    private MessageData data;

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public MessageData getData() { return data; }
    public void setData(MessageData data) { this.data = data; }
}

// 消息数据类
class MessageData {
    private String from;
    private String to;
    private String content;
    private String time;

    public String getFrom() { return from; }
    public void setFrom(String from) { this.from = from; }
    public String getTo() { return to; }
    public void setTo(String to) { this.to = to; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }
}