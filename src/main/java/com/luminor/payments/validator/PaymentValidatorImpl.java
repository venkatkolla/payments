package com.luminor.payments.validator;

import com.luminor.payments.model.Currency;
import com.luminor.payments.model.Payment;
import com.luminor.payments.model.PaymentType;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * <h1>PaymentValidatorImpl</h1>
 * PaymentValidatorImpl  class validates the payment model.
 * This is the form level validation to check various combinations of
 * payment and currencies.
 *
 *
 *
 * @author  Venkat Kolla
 * @version 1.0
 * @since   2020-02-08
 */

public class PaymentValidatorImpl implements ConstraintValidator<PaymentValidator, Payment> {

    public void initialize(PaymentValidator paymentValidator) {

    }

    public boolean isValid(Payment object, ConstraintValidatorContext context) {
        if (!(object instanceof Payment)) {
            throw new IllegalArgumentException("@Payment only applies to Payment");
        }
        Payment payment = (Payment) object;


        if (payment.getPaymentType().equalsIgnoreCase(PaymentType.TYPE1.toString())) {
            if(payment.getCurrency().equalsIgnoreCase(Currency.EUR.toString()) &&
                StringUtils.hasText(payment.getDetails())) {
                return true;
            }else {
                context.buildConstraintViolationWithTemplate( "Payment Type 1 is applicable for only EUR and details are missing" )
                        .addConstraintViolation();
                return false;
            }
        } else if (payment.getPaymentType().equalsIgnoreCase(PaymentType.TYPE2.toString())) {
            if(payment.getCurrency().equalsIgnoreCase(Currency.USD.toString()) &&
                    StringUtils.hasText(payment.getDetails())) {
                return true;
            }else {
                context.buildConstraintViolationWithTemplate(  "Payment Type 2 is applicable for only USD and details are missing" )
                        .addConstraintViolation();
                return false;
            }
        } else {
           if (payment.getPaymentType().equalsIgnoreCase(PaymentType.TYPE3.toString())) {
                if(StringUtils.hasText(payment.getBic()))
                    return true;
            }else {
               context.buildConstraintViolationWithTemplate( "Details are missing." )
                       .addConstraintViolation();
                return false;
            }
        }
       return false;
    }

}
