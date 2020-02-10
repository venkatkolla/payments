package com.luminor.payments.service;

import com.luminor.payments.model.Payment;
import com.luminor.payments.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.ServiceMode;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * <h1>PaymentServiceImpl</h1>
 * Payment Service Impl  has create payments
 * and retrieve payment methods which will pass request to repository.
 *
 *
 * @author  Venkat Kolla
 * @version 1.0
 * @since   2020-02-08
 */

@Service
public class PaymentServiceImpl implements  PaymentService{

    @Autowired
    PaymentRepository paymentRepository;

    public List<Payment> getAllPayments() {

        return (List<Payment>)paymentRepository.findAll();
    }

    public Optional<Payment> getPaymentById(long paymentId) {

        return paymentRepository.findById(paymentId);
    }

    public Set<Long> getActivePaymentsByAmount(BigDecimal amount){
        return paymentRepository.findIsPaymentsCancelledAndAmount(false,amount);
    }

    public Payment makePayment(Payment payment){
       return paymentRepository.save(payment);
    }
}
