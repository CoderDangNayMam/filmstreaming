package com.vti.trangwebxemphimv2.config.Annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


/**
 * ConstrainValidator<A,T>;
 * trong đó A là annotation vừa tạo ở bước 1
 *          B là kiểu dữ liệu mình muốn validate
 */
public class NotAdminValidator implements ConstraintValidator<NotAdmin, String> {
    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        return !name.contains("ADMIN");
    }
}
