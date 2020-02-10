package com.luminor.payments.controller;

import com.luminor.payments.model.Payment;
import com.luminor.payments.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * <h1>PaymentController</h1>
 * Payment Controller creates payments
 * and retrieves payment objects based on
 * user queries.
 * <p>
 *
 *
 *
 * @author  Venkat Kolla
 * @version 1.0
 * @since   2020-02-08
 */
@RestController
@RequestMapping("/luminor/api")
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    /**
     * This method is retrieves all payments
     *
     * @return List<Payment>
     */
    @GetMapping("/payment")
    public List<Payment> getPayments() {

        return paymentService.getAllPayments();
    }

    /**
     * This method is retrieves payment object by paymentId
     * @pram paymentId
     * @return List<Payment>
     */
    @GetMapping("/payment/{paymentId}")
    public Payment getPaymentById(@PathVariable("paymentId") Long paymentId) {
        Optional<Payment>  payment= paymentService.getPaymentById(paymentId);
        return payment.get();
    }

    /**
     * This method creates payment object in DB
     * and returns payment id.
     * @pram Payment
     * @return Long
     */
    @PostMapping("/payment")
    @ResponseStatus(HttpStatus.CREATED)
    public Long makePayment(@Valid @RequestBody Payment payment) {
       paymentService.makePayment(payment);
       return payment.getPaymentId();
    }

    /**
     * This method retrieves active payments for given amount.
     * and returns payment ids set.
     * @pram Payment
     * @return Set<Long>
     *
     */
    @GetMapping("/payment/active/{amount}")
    public Set<Long> getActivePaymentsByAmount(@PathVariable("amount") BigDecimal amount) {
        return paymentService.getActivePaymentsByAmount(amount);
    }



}
