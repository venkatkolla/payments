package com.luminor.payments.validator;

import javax.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
import javax.validation.Payload;

@Constraint(validatedBy = PaymentValidatorImpl.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PaymentValidator {
    String message() default "Payment Error";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };

}
