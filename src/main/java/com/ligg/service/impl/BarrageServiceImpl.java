package com.ligg.service.impl;

import com.ligg.mapper.BarrageMapper;
import com.ligg.pojo.Barrage;
import com.ligg.service.BarrageService;
import com.ligg.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class BarrageServiceImpl implements BarrageService {

    @Autowired
    BarrageMapper barrageMapper;

    // 发送弹幕
    @Override
    public String sendBarrage(Barrage barrage) {
        Map<String, Object> map = ThreadLocalUtil.get();
        barrage.setUserId((Integer) map.get("id"));
        barrageMapper.sendBarrage(barrage);
        return null;
    }
}