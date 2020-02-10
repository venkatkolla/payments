package com.luminor.payments.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.luminor.payments.model.Currency;
import com.luminor.payments.model.Payment;
import com.luminor.payments.model.PaymentType;
import com.luminor.payments.repository.PaymentRepository;
import com.luminor.payments.service.PaymentService;
import com.luminor.payments.service.PaymentServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.match.ContentRequestMatchers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;


import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.CoreMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(PaymentController.class)
public class PaymentControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PaymentServiceImpl paymentService;

     @MockBean
    private PaymentRepository paymentRepository;


    private Payment payment;

    @Before
    public void setUp(){
        // given
        payment = getPayment();
    }

    @Test
    public void givenPayment_whenGetPaymentById_thenReturnJson()
            throws Exception {
        Payment pmt = getPayment();
        pmt.setPaymentId(1L);
        // given
        given(paymentService.getPaymentById(pmt.getPaymentId()))
                .willReturn(Optional.of(pmt));


        mvc.perform(MockMvcRequestBuilders.get("/luminor/api/payment/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currency", is(Currency.EUR.toString())))
                .andExpect(jsonPath("$.creditorIban", is("GB98 MIDL 0700 9312 3456 78")));

    }

    @Test
    public void givenPaymentCreation_thenReturnJson()
            throws Exception {
        // given
        Payment pmt = getPayment();
        given(paymentService.makePayment(pmt))
                .willReturn(pmt);
         pmt.setPaymentDate(null);
         pmt.setPaymentId(null);

         mvc.perform(MockMvcRequestBuilders.post("/luminor/api/payment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJSon(pmt))
                        .characterEncoding("utf-8")
                        .accept(MediaType.APPLICATION_JSON))
                     .andExpect(status().isCreated());
    }


    private String convertObjectToJSon(Payment payment) throws Exception {
        ObjectMapper om = new ObjectMapper();
        String json = om.writeValueAsString(payment);
        return json;
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
