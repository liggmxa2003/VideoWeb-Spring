package com.ligg.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ligg.pojo.ChatMessage;
import com.ligg.pojo.Message;
import com.ligg.pojo.MessageData;
import com.ligg.service.ChatMessageService;
import com.ligg.service.impl.OfflineMessageServiceImpl;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@ServerEndpoint("/ws/chat")
@Component
public class WebSocketServer {
    // 使用 DateTimeFormatter 处理日期时间
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // 配置 ObjectMapper
    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

    private static final Map<String, Session> sessions = new ConcurrentHashMap<>();
    private static ChatMessageService chatMessageService;
    private static OfflineMessageServiceImpl offlineMessageService;
    // 存储用户在线状态
    private static final Map<String, Boolean> onlineStatus = new ConcurrentHashMap<>();
    private String username;

    @Autowired
    public void setChatMessageService(ChatMessageService service) {
        WebSocketServer.chatMessageService = service;
    }

    @Autowired
    public void setOfflineMessageService(OfflineMessageServiceImpl service) {
        WebSocketServer.offlineMessageService = service;
    }

    /**
     * 用户连接
     * @param session
     */
    @OnOpen
    public void onOpen(Session session) {
        try {
            Map<String, String> params = parseQueryString(session.getQueryString());
            this.username = params.get("username");

            if (this.username == null || this.username.isEmpty()) {
                throw new IllegalArgumentException("username is required");
            }

            sessions.put(this.username, session);
            onlineStatus.put(this.username, true); // 设置用户在线
            log.info("用户连接成功: {}", this.username);

            // 广播用户上线状态
            broadcastUserStatus(this.username, true);

            // 检查并发送离线消息
            sendOfflineMessages();
        } catch (Exception e) {
            log.error("连接失败: {}", e.getMessage());
            try {
                session.close();
            } catch (Exception ignored) {}
        }
    }

    /**
     * 接收消息
     * @param messageStr
     * @param session
     */
    @OnMessage
    public void onMessage(String messageStr, Session session) {
        try {
            if (this.username == null) {
                System.err.println("用户未认证");
                return;
            }

            Message message = objectMapper.readValue(messageStr, Message.class);

            if ("chat".equals(message.getType()) && message.getData() != null) {
                String to = message.getData().getTo();
                if (to == null || to.isEmpty()) {
                    System.err.println("接收者不能为空");
                    return;
                }

                // 创建消息对象
                ChatMessage chatMessage = createChatMessage(message);

                // 保存消息到数据库
                chatMessageService.saveMessage(chatMessage);

                // 发送消息给接收者
                Message responseMessage = buildResponseMessage(chatMessage);
                sendMessageToUser(to, responseMessage);
            }
        } catch (Exception e) {
            System.err.println("处理消息失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 错误处理
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.err.println("用户 " + this.username + " 发生错误: " + error.getMessage());
    }

    /**
     * 用户断开连接
     * @param session
     */
    @OnClose
    public void onClose(Session session) {
        if (this.username != null) {
            sessions.remove(this.username);
            onlineStatus.put(this.username, false); // 设置用户离线

            // 广播用户离线状态
            broadcastUserStatus(this.username, false);
            System.out.println("用户断开连接: " + this.username);
        }
    }

    /**
     * 创建聊天消息对象
     */
    private ChatMessage createChatMessage(Message message) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setFromUser(this.username);
        chatMessage.setToUser(message.getData().getTo());
        chatMessage.setContent(message.getData().getContent());

        // 处理发送时间
        LocalDateTime sendTime;
        try {
            String timeStr = message.getData().getTime();
            sendTime = timeStr != null && !timeStr.isEmpty() ?
                    LocalDateTime.parse(timeStr, DATE_FORMATTER) :
                    LocalDateTime.now();
        } catch (Exception e) {
            // 如果时间解析失败，使用当前时间
            sendTime = LocalDateTime.now();
            System.err.println("时间解析失败，使用当前时间: " + e.getMessage());
        }
        chatMessage.setSendTime(sendTime);

        return chatMessage;
    }

    /**
     * 构建响应消息
     */
    private Message buildResponseMessage(ChatMessage chatMessage) {
        Message responseMessage = new Message();
        responseMessage.setType("chat");

        MessageData responseData = new MessageData();
        responseData.setFrom(chatMessage.getFromUser());
        responseData.setContent(chatMessage.getContent());
        // 格式化时间为字符串
        responseData.setTime(chatMessage.getSendTime().format(DATE_FORMATTER));
        responseMessage.setData(responseData);

        return responseMessage;
    }

    /**
     * 发送消息给指定用户
     */
    private void sendMessageToUser(String toUser, Message message) {
        try {
            Session receiverSession = sessions.get(toUser);
            if (receiverSession != null && receiverSession.isOpen()) {
                String messageStr = objectMapper.writeValueAsString(message);
                receiverSession.getBasicRemote().sendText(messageStr);
                log.info("消息发送成功: {}", messageStr);
            } else {
                log.info("接收者不在线: {}, 存储为离线消息", toUser);
                // 将消息转换为 ChatMessage 并存储
                ChatMessage chatMessage = new ChatMessage();
                chatMessage.setFromUser(message.getData().getFrom());
                chatMessage.setToUser(toUser);
                chatMessage.setContent(message.getData().getContent());
                chatMessage.setSendTime(LocalDateTime.parse(
                        message.getData().getTime(),
                        DATE_FORMATTER
                ));
                // 存储离线消息
                offlineMessageService.storeOfflineMessage(toUser, chatMessage);
            }
        } catch (Exception e) {
            log.error("发送消息失败: {}", e.getMessage());
        }
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

    /**
     * 发送离线消息
     */
    private void sendOfflineMessages() {
        try {
            List<ChatMessage> offlineMessages = offlineMessageService.getOfflineMessages(this.username);
            if (!offlineMessages.isEmpty()) {
                for (ChatMessage message : offlineMessages) {
                    Message responseMessage = buildResponseMessage(message);
                    sendMessageToUser(this.username, responseMessage);
                }
                // 发送完成后删除离线消息
                offlineMessageService.deleteOfflineMessages(this.username);
            }
        } catch (Exception e) {
            log.error("发送离线消息失败: {}", e.getMessage());
        }
    }

    /**
     * 广播用户状态变化
     */
    private void broadcastUserStatus(String username, boolean isOnline) {
        Message statusMessage = new Message();
        statusMessage.setType("status");

        MessageData data = new MessageData();
        data.setUsername(username);
        data.setOnline(isOnline);
        statusMessage.setData(data);

        // 广播给所有在线用户
        sessions.values().forEach(session -> {
            try {
                if (session.isOpen()) {
                    session.getAsyncRemote().sendText(objectMapper.writeValueAsString(statusMessage), result -> {
                        if (result.getException() != null) {
                            log.error("Failed to send message to user {}: {}", username, result.getException().getMessage());
                        }
                    });
                }
            } catch (Exception e) {
                log.error("Error broadcasting user status: {}", e.getMessage());
            }
        });
    }

    /**
     * 检查用户是否在线
     */
    public static boolean isUserOnline(String username) {
        return onlineStatus.getOrDefault(username, false);
    }
}
