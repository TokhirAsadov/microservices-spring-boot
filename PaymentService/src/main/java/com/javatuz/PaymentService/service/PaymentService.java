package com.javatuz.PaymentService.service;

import com.javatuz.PaymentService.model.PaymentRequest;
import com.javatuz.PaymentService.model.PaymentResponse;

public interface PaymentService {
    long doPayment(PaymentRequest paymentRequest);

    PaymentResponse getPaymentDetailsByOrderId(String orderId);
}
