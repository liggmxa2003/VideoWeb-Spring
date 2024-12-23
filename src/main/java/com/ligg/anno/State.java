package com.ligg.anno;

import com.ligg.validation.StateValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

// 自定义注解
@Documented
@Target({ FIELD}) // 注解在字段上
@Retention(RUNTIME)// 运行时生效
@Constraint(validatedBy = {StateValidation.class}) // 指定校验器
public @interface State {
    // 提示信息
    String message() default "只允许是已发布或草稿";
    // 分组
    Class<?>[] groups() default {};
    // 负载
    Class<? extends Payload>[] payload() default {};
}
