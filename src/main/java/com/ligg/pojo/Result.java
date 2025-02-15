package com.ligg.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//统一响应结果
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    private Integer code;//业务状态码  200-成功  401-失败
    private String message;//提示信息
    private T data;//响应数据

    public static <E> Result<E> success(E data) {
        return new Result<>(200, "操作成功", data);
    }

    public static <T>Result<T> success() {
        return new Result<>(200, "操作成功", null);
    }

    public static <T>Result<T> error(String message) {
        return new Result<>(401, message, null);
    }
}
