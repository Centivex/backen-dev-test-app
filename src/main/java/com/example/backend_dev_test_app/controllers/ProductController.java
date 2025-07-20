package com.example.backend_dev_test_app.controllers;

import com.example.backend_dev_test_app.api.ProductApi;
import com.example.backend_dev_test_app.models.Product;
import com.example.backend_dev_test_app.services.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController implements ProductApi {
    @Autowired
    private ProductServiceImpl productService;


    @GetMapping("/{productId}/similar")
    public ResponseEntity<List<Product>> getSimilarProducts(@PathVariable String productId){
        return ResponseEntity.ok(productService.getSimilarProducts(productId));
    }
}
