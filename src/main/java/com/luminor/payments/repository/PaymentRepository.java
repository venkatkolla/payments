package com.luminor.payments.repository;

import com.luminor.payments.model.Payment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Set;


/**
 * <h1>PaymentRepository</h1>
 * PaymentRepository  extends JPA repository and provides CRUD operations for Payment
 *
 *
 * @author  Venkat Kolla
 * @version 1.0
 * @since   2020-02-08
 */
@Repository
public interface PaymentRepository extends CrudRepository<Payment, Long> {


    /**
     * This method retrieves active/cancelled payments for a given amount
     * using JPQL query.
     *
     * @return List<Long>
     */
    @Query("SELECT p.paymentId FROM Payment p WHERE p.isPaymentCancelled = ?1 and p.amount >= ?2")
    Set<Long> findIsPaymentsCancelledAndAmount(boolean isPaymentCancelled, BigDecimal amount);

}
