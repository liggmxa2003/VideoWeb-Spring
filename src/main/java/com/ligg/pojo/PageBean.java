package com.ligg.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
//分页返回结果对象
public class PageBean <T>{
    private Long total;//总条数
    private List<T> items;//当前页数据集合
}
