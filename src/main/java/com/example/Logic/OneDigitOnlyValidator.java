package com.example.Logic;

import com.example.CustomValidators.OneDigitOnly;
import com.example.Enum.OneDigitOnlyType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by Administrator on 2017-05-17.
 */
public class OneDigitOnlyValidator implements ConstraintValidator<OneDigitOnly, String>{
    private OneDigitOnlyType[] allowedTypes;
    private boolean ignoreCase;

    @Override
    public void initialize(OneDigitOnly constraint) {
        allowedTypes = constraint.value();
        ignoreCase = constraint.ignoreCase();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null){
            return true;
        }

        for (OneDigitOnlyType type : allowedTypes){
            /*switch (type) {
                case DIGIT:*/
                    if (isDigit(value)) {
                        return true;
                    }
                    //break;

            //}
        }return false;
    }

    /*private boolean isLetter(String value) {
        return true;
    }*/

    private boolean isDigit(String value) {
        return equalsChar(value, "1") || equalsChar(value, "2") || equalsChar(value, "3") ||
                equalsChar(value, "4") || equalsChar(value, "5") || equalsChar(value, "6")
                || equalsChar(value, "7") || equalsChar(value, "8") || equalsChar(value, "9")
                || equalsChar(value, "");
    }

    private boolean equalsChar(String value1, String value2) {
        return ignoreCase ? value1.equalsIgnoreCase(value2) : value1.equals(value2);
    }


}
