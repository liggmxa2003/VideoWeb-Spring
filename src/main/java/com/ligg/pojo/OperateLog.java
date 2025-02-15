package com.ligg.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperateLog {
    private Integer id;
    private Long operateUserId;//操作人id
    private LocalDateTime operateTime;//操作时间
    private String className;//类名
    private String methodName;//方法名
    private String methodParams;//方法参数
    private String returnValue;//方法返回值
    private Long costTime;//方法耗时
}
