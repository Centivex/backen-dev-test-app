package com.example.backend_dev_test_app.controllers;

import com.example.backend_dev_test_app.models.Product;
import com.example.backend_dev_test_app.services.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductServiceImpl productService;

    @GetMapping("/{productId}/similar")
    public List<Product> getSimilarProducts(@PathVariable String productId){
        return productService.getSimilarProducts(productId);
    }
}
