package com.luminor.payments.repository;

import com.luminor.payments.model.Currency;
import com.luminor.payments.model.Payment;
import com.luminor.payments.model.PaymentType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(SpringRunner.class)
@DataJpaTest
public class PaymentRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PaymentRepository paymentRepository;

    private Payment payment;

    @Before
    public void setUp(){
        // given
         payment = getPayment();
    }


    @Test
    public void whenFindById_thenReturnPayment() {
        // given
       createPayment(payment);

        // when
        Optional<Payment> resultPayment = paymentRepository.findById(payment.getPaymentId());

        // then
        assertEquals(resultPayment.get().getCurrency(),
                payment.getCurrency());

    }

    @Test
    public void whenCreatePayment_thenReturnPayment() {
        // given
        createPayment(payment);

        // when
        Optional<Payment> resultPayment = paymentRepository.findById(payment.getPaymentId());

        // then
        assertNotNull(resultPayment.get().getPaymentId());
        assertEquals(resultPayment.get().getPaymentType(),
                payment.getPaymentType());

    }

    @Test
    public void whenfindIsPaymentsCancelledAndAmount_thenReturnPayment() {
        // given
        Payment payment1 = getPayment();
        createPayment(payment1);

        Payment payment2 = getPayment();
        payment2.setAmount(new BigDecimal(50));
        createPayment(payment2);

        // when
        Set<Long> results = paymentRepository.findIsPaymentsCancelledAndAmount(false,new BigDecimal(50));

        // then
        assertEquals(results.size(),2l);

    }

   private void createPayment(Payment payment) {
       entityManager.persist(payment);
       entityManager.flush();
   }

   private Payment getPayment() {
      Payment payment = new Payment();
       payment.setPaymentType(PaymentType.TYPE1.toString());
       payment.setCurrency(Currency.EUR.toString());
       payment.setAmount(new BigDecimal(100));
       payment.setCreditorIban("GB98 MIDL 0700 9312 3456 78");
       payment.setDebtorIban("BE71 0961 2345 6769");
       payment.setBic("DABAIE2D");
       payment.setDetails("details");
       payment.setPaymentDate(LocalDateTime.now());
       return  payment;
   }


}
