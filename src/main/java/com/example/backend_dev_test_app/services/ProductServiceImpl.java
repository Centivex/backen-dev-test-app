package com.example.backend_dev_test_app.services;

import com.example.backend_dev_test_app.models.Product;
import com.example.backend_dev_test_app.services.interfaces.IProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements IProductService {

    private  final RestTemplate restTemplate = new RestTemplate();
    private  final String url= "http://localhost:3001";


    @Override
    public List<Product> getSimilarProducts(String productId) {

        try {
            ResponseEntity<String[]> response = restTemplate.getForEntity(
                    url + "/product/" + productId + "/similarids", String[].class
            );
            String[] similarIds = response.getBody();

            List<Product> products = new ArrayList<>();
            for (String id : similarIds) {
                Product p = restTemplate.getForObject(url + "/product/" + id, Product.class);
                products.add(p);
            }
            return products;

        } catch (HttpClientErrorException.NotFound e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Error fetching data");
        }
    }
}
