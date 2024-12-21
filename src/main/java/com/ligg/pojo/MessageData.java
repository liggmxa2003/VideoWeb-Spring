package com.ligg.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageData {
    private String chatId;
    private String username;
    private String content;
    private Boolean online;
    private String time;
    private String from;
    private String to;
}
