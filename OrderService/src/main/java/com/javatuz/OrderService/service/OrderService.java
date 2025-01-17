package com.javatuz.OrderService.service;

import com.javatuz.OrderService.model.OrderRequest;
import com.javatuz.OrderService.model.OrderResponse;

public interface OrderService {
    long placeOrder(OrderRequest orderRequest);

    OrderResponse getOrderDetails(long orderId);
}
