package com.ligg.anno;

import com.ligg.validation.SexValidation;
import com.ligg.validation.StateValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target({ FIELD}) // 注解在字段上
@Retention(RUNTIME)
@Constraint(validatedBy = {SexValidation.class}) // 指定校验器
public @interface Sex {
    // 提示信息
    String message() default "不允许的性别范围";
    // 分组
    Class<?>[] groups() default {};
    // 负载
    Class<? extends Payload>[] payload() default {};
}
