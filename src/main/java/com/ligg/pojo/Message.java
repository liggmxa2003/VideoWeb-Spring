package com.ligg.pojo;

import lombok.Data;

@Data
public class Message {
    private Long id;
    private Long senderId;//发送者
    private Long receiverId;//接收者
    private String content;//内容
    private String createTime;//创建时间
    private String type;//类型
    private MessageData data;//数据
}
