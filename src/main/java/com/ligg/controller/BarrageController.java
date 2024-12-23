package com.ligg.controller;

import com.ligg.pojo.Barrage;
import com.ligg.pojo.Result;
import com.ligg.service.BarrageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/barrage")
public class BarrageController {

    @Autowired
    BarrageService barrageService;

    //发送弹幕
    @PostMapping("/send")
    public Result<String> sendBarrage(@RequestBody Barrage barrage) {
        return Result.success(barrageService.sendBarrage(barrage));
    }
    //获取弹幕
    @GetMapping("/get")
    public Result<List<Barrage>> getBarrage(@RequestParam Integer videoId) {
        return Result.success(barrageService.getBarrage(videoId));
    }
}
