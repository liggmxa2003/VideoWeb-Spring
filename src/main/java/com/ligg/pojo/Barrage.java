package com.ligg.pojo;

import lombok.Data;

import java.util.Date;


@Data
public class Barrage {
    private Integer id;
    private Integer videoId;//视频id
    private Integer userId;//用户id
    private String content;//弹幕内容
    private String color;//弹幕颜色
    private Double timePoint;//弹幕时间
    private Date createTime;//创建时间
}