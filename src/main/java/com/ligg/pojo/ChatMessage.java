package com.ligg.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {
    private Long id;// 消息ID
    private String fromUser;// 发送者用户名
    private String toUser;// 接收者用户名
    @Pattern(regexp = "[a-z A-Z0-9]{1,150}")
    private String content;// 消息内容
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime sendTime;// 发送时间
    private Boolean isRead;// 是否已读
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
}
