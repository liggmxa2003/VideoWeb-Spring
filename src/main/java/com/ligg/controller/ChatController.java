package com.ligg.controller;

import com.ligg.pojo.Result;
import com.ligg.pojo.User;
import com.ligg.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class ChatController {

    @Autowired
    UserService userService;

    //获取聊天对象
    @GetMapping("/chat")
    public Result<List<User>> userList(String username){
        //判断聊天对象是否为自己
        String user = userService.findUsername(username);
        if (user!=null)
            return Result.error(user);
        List<User> list = userService.findByUserChat(username);
        return Result.success(list);
    }
}
