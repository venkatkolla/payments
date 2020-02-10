package com.luminor.payments.service;

import com.luminor.payments.model.Currency;
import com.luminor.payments.model.Payment;
import com.luminor.payments.model.PaymentType;
import com.luminor.payments.repository.PaymentRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class PaymentServiceImplTest {

    @TestConfiguration
    static class PaymentServiceImplTestContextConfiguration {

        @Bean
        public PaymentService PaymentService() {
            return new PaymentServiceImpl();
        }
    }

    @Autowired
    private PaymentService paymentService;

    @MockBean
    private PaymentRepository paymentRepository;

    private Payment payment;

    @Before
    public void setUp() {

            // given
        payment = new Payment();
        payment.setPaymentId(1L);
        payment.setPaymentType(PaymentType.TYPE1.toString());
        payment.setCurrency(Currency.EUR.toString());
        payment.setAmount(new BigDecimal(100));
        payment.setCreditorIban("GB98 MIDL 0700 9312 3456 78");
        payment.setDebtorIban("BE71 0961 2345 6769");
        payment.setBic("DABAIE2D");
        payment.setPaymentDate(LocalDateTime.now());


    }

    @Test
    public void whenGetPaymentById_thenReturnPayment() {

        // when
        Mockito.when(paymentRepository.findById(payment.getPaymentId()))
                .thenReturn(Optional.of(payment));

        Optional<Payment> resultPayment = paymentService.getPaymentById(payment.getPaymentId());

        // then
        assertEquals(resultPayment.get().getCurrency(),
                payment.getCurrency());

    }

}
