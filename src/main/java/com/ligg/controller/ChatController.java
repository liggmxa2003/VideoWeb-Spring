package com.ligg.controller;

import com.ligg.component.WebSocketServer;
import com.ligg.pojo.ChatMessage;
import com.ligg.pojo.Result;
import com.ligg.pojo.user.User;
import com.ligg.service.ChatMessageService;
import com.ligg.service.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class ChatController {

    @Autowired
    UserService userService;
    private final ChatMessageService chatMessageService;

    //获取聊天对象
    @GetMapping("/chat")
    public Result<List<User>> userList(String username) {
        //判断聊天对象是否为自己
        String user = userService.findUsername(username);
        if (user != null)
            return Result.error(user);
        List<User> list = userService.findByUserChat(username);
        return Result.success(list);
    }

    /**
     * 获取聊天历史记录
     *
     * @param user1 用户1
     * @param user2 用户2
     * @return 聊天记录列表
     */
    @GetMapping("/history")
    public Result<List<ChatMessage>> getChatHistory(
            @RequestParam String user1,
            @RequestParam String user2) {
        return Result.success(chatMessageService.getChatHistory(user1, user2));
    }

    /**
     * 获取未读消息数量
     *
     * @param username 用户名
     * @return 未读消息数量
     */
    @GetMapping("/unread")
    public Result<Integer> getUnreadCount(@RequestParam String username) {
        return Result.success(chatMessageService.getUnreadCount(username));
    }

    /**
     * 标记消息为已读
     *
     * @param fromUser 发送者
     * @param toUser   接收者
     * @return 操作结果
     */
    @PostMapping("/mark-read")
    public Result<Void> markAsRead(
            @RequestParam String fromUser,
            @RequestParam String toUser) {
        chatMessageService.markAsRead(fromUser, toUser);
        return Result.success();
    }

    /**
     * 获取用户在线状态
     */
    @GetMapping("/online-status")
    public Result<Map<String, Boolean>> getOnlineStatus(@RequestParam List<String> usernames) {
        Map<String, Boolean> statusMap = new HashMap<>();
        for (String username : usernames) {
            statusMap.put(username, WebSocketServer.isUserOnline(username));
        }
        return Result.success(statusMap);
    }
}
