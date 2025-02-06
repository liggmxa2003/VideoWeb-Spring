package com.ligg.controller;

import com.ligg.pojo.Result;
import com.ligg.service.User.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/email")
public class EmailController {
    @Autowired
    UserService userService;

    //发送注册邮箱验证码
    @PostMapping
    public Result<String> sendEmail(@RequestParam("email") @Email String email, HttpSession session) {
        String s = userService.sendValidateCode(email, session.getId(),false);
        if (s == null)
            return Result.success();
        return Result.error(s);
    }

    //发送重置密码邮箱验证码
    @PostMapping("/forget")
    public Result<String> forget(@RequestParam("email") @Email String email, HttpSession session) {
        String s = userService.sendValidateCode(email, session.getId(),true);
        if (s == null)
            return Result.success();
        return Result.error(s);
    }
}
