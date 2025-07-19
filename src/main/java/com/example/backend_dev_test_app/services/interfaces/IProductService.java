package com.example.backend_dev_test_app.services.interfaces;

import com.example.backend_dev_test_app.models.Product;

import java.util.List;

public interface IProductService {
    List<Product> getSimilarProducts(String productId);
}
