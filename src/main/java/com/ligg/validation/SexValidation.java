package com.ligg.validation;

import com.ligg.anno.Sex;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SexValidation implements ConstraintValidator<Sex, Integer> {

    /**
     *
     * @param value 被校验的值
     * @param context 上下文
     */
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null)
            return true;
        return value == 1 || value == 2 || value == 0;
    }
}
