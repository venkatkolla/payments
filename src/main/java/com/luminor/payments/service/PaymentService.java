package com.luminor.payments.service;

import com.luminor.payments.model.Payment;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PaymentService {

     List<Payment> getAllPayments();

     Optional<Payment> getPaymentById(long paymentId);

     Set<Long> getActivePaymentsByAmount(BigDecimal amount);

     Payment makePayment(Payment payment);
}
