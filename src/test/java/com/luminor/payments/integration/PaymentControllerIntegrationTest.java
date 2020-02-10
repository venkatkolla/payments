package com.luminor.payments.integration;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.luminor.payments.model.Currency;
import com.luminor.payments.model.Payment;
import com.luminor.payments.model.PaymentType;
import com.luminor.payments.repository.PaymentRepository;
import com.luminor.payments.service.PaymentServiceImpl;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PaymentControllerIntegrationTest {


    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private PaymentRepository paymentRepository;

    @MockBean
    private PaymentServiceImpl paymentService;

    @Before
    public void init() {
        Payment payment = getPayment();
        payment.setPaymentId(1L);

        when(paymentService.getPaymentById(1L)).thenReturn(Optional.of(payment));
    }


    @Test
    public void findPaymentByIdTest() throws Exception {

        Payment pmt = getPayment();
        pmt.setPaymentId(1L);

        String  expectedResponse = convertObjectToJSon(pmt);

        ResponseEntity<String> response = restTemplate.getForEntity("/luminor/api/payment/1", String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());

        JSONAssert.assertEquals(expectedResponse.toString(), response.getBody(), false);

        verify(paymentService, times(1)).getPaymentById(1L);

    }

    @Test
    public void makePaymentTest() throws JSONException {


        ResponseEntity<String> response = restTemplate.postForEntity("/luminor/api/payment",
                getPayment(),String.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());


        verify(paymentService, times(1)).makePayment(any());

    }

    @Test
    public void makePaymentTestFailWrongPaymentType() throws JSONException {
            //given
            Payment pay = getPayment();
            pay.setPaymentType("TT");

            ResponseEntity<String> response = restTemplate.postForEntity("/luminor/api/payment",
                pay,String.class);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void makePaymentTestFailTYPE1PmtTypeAndUSD() throws JSONException {
        //given
        Payment pay = getPayment();
        pay.setCurrency("USD");
        pay.setPaymentType("TYPE1");

        ResponseEntity<String> response = restTemplate.postForEntity("/luminor/api/payment",
                pay,String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void makePaymentTestFailWrongCurrency() throws JSONException {
        //given
        Payment pay = getPayment();
        pay.setCurrency("CHF");

        ResponseEntity<String> response = restTemplate.postForEntity("/luminor/api/payment",
                pay,String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
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
        payment.setCancellationFee(new BigDecimal(0));
        return  payment;
    }

    private String convertObjectToJSon(Payment payment) throws Exception {
        ObjectMapper om = new ObjectMapper();
        String json = om.writeValueAsString(payment);
        return json;
    }


}
