package com.ligg.pojo;

import lombok.Data;

import java.util.Date;


@Data
public class Barrage {
    private String videoId;
    private String userId;
    private String content;
    private String color;
    private Double timePoint;
    private Date createdAt;
}