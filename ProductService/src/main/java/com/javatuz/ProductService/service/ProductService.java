package com.javatuz.ProductService.service;

import com.javatuz.ProductService.model.ProductRequest;
import com.javatuz.ProductService.model.ProductResponse;

public interface ProductService {
    long addProduct(ProductRequest productRequest);

    ProductResponse getProductById(long productId);

    void reduceQuantity(long productId, long quantity);
}
