package com.example.backend_dev_test_app.services;

import com.example.backend_dev_test_app.exceptions.ExternalServiceException;
import com.example.backend_dev_test_app.exceptions.NotFoundException;
import com.example.backend_dev_test_app.models.Product;
import com.example.backend_dev_test_app.services.interfaces.IProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements IProductService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String url = "http://localhost:3001";

    @Override
    public List<Product> getSimilarProducts(String productId) {
        String[] similarIds;

        try {
            similarIds = restTemplate.getForObject(
                    url + "/product/" + productId + "/similarids", String[].class);
        } catch (HttpClientErrorException.NotFound ex) {
            throw new NotFoundException("Product " + productId + " not found.");
        } catch (RestClientException ex) {
            throw new ExternalServiceException("Error fetching similar product IDs for product " + productId);
        }

        if (similarIds == null || similarIds.length == 0) {
            throw new NotFoundException("No similar products found for product " + productId);
        }

        List<Product> products = new ArrayList<>();
        for (String id : similarIds) {
            try {
                Product product = restTemplate.getForObject(
                        url + "/product/" + id, Product.class);
                if (product != null) products.add(product);
            } catch (HttpClientErrorException.NotFound ex) {
                throw new NotFoundException("Similar product " + id + " not found.");
            } catch (RestClientException ex) {
                throw new ExternalServiceException("Error fetching product details for id: " + id);
            }
        }

        return products;
    }
}