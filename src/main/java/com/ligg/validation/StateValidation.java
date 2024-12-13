package com.ligg.validation;

import com.ligg.anno.State;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StateValidation implements ConstraintValidator<State, String> {
    /**
     *
     * @param value 将来要校验的数据
     * @param context
     * @return
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null){
            return false;
        }
        if ("已发布".equals(value) || "草稿".equals(value)) {
            return true;
        }
        return false;
    }
}
