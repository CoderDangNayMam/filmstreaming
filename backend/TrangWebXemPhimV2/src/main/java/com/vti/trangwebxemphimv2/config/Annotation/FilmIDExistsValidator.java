package com.vti.trangwebxemphimv2.config.Annotation;

import com.vti.trangwebxemphimv2.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FilmIDExistsValidator implements ConstraintValidator<FilmIDExists, Integer> {

    @Autowired
    FilmRepository filmRepository;

    @Override
    public boolean isValid(Integer integer, ConstraintValidatorContext constraintValidatorContext) {
        return filmRepository.existsById(integer);
    }
}
