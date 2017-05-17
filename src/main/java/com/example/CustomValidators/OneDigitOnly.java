package com.example.CustomValidators;

import com.example.Enum.OneDigitOnlyType;
import com.example.Logic.OneDigitOnlyValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Administrator on 2017-05-17.
 */
@Documented
@Constraint(validatedBy = OneDigitOnlyValidator.class)
@Target({ METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
public @interface OneDigitOnly {

    String message() default "{com.example.CustomValidators.SudokuBox.message}";
    Class<?>[] groups() default{};
    Class<? extends Payload>[] payload() default {};
    OneDigitOnlyType[] value() default { };
    boolean ignoreCase() default false;
}
