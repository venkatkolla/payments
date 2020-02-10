package com.luminor.payments.validator;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * <h1>EnumValidatorImpl</h1>
 * EnumValidatorImpl  class validates the payment model
 * paymentType and Currency.
 *
 *
 * @author  Venkat Kolla
 * @version 1.0
 * @since   2020-02-08
 */

public class EnumValidatorImpl implements ConstraintValidator<EnumValidator, String> {

    List<String> valueList = null;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return valueList.contains(value.toUpperCase());
    }

    @Override
    public void initialize(EnumValidator constraintAnnotation) {
        valueList = new ArrayList<String>();
        Class<? extends Enum<?>> enumClass = constraintAnnotation.enumClazz();

        @SuppressWarnings("rawtypes")
        Enum[] enumValArr = enumClass.getEnumConstants();

        for (@SuppressWarnings("rawtypes") Enum enumVal : enumValArr) {
            valueList.add(enumVal.toString().toUpperCase());
        }
    }

}
