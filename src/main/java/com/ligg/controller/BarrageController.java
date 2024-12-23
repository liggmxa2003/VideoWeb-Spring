package com.ligg.controller;

import com.ligg.pojo.Barrage;
import com.ligg.pojo.Result;
import com.ligg.service.BarrageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/barrage")
public class BarrageController {

    @Autowired
    BarrageService barrageService;

    //发送弹幕
    @PostMapping
    public Result<String> sendBarrage(@RequestBody Barrage barrage) {
        return Result.success(barrageService.sendBarrage(barrage));
    }
}
