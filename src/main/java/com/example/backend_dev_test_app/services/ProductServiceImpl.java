package com.example.backend_dev_test_app.services;

import com.example.backend_dev_test_app.models.Product;
import com.example.backend_dev_test_app.services.interfaces.IProductService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements IProductService {

    private  final RestTemplate restTemplate = new RestTemplate();
    private  final String url= "http://localhost:3001";


    @Override
    public List<Product> getSimilarProducts(String productId) {
        List<Product> result = new ArrayList<>();

        try{

            String[] similarIds = restTemplate.getForObject(
                    url + "/product/" + productId + "/similarids",
                    String[].class
            );

            if (similarIds != null) {
                for (String id : similarIds) {
                    try {
                        Product product = restTemplate.getForObject(
                                url + "/product/" + id,
                                Product.class
                        );
                        if (product != null) {
                            result.add(product);
                        }
                    } catch (HttpClientErrorException ex) {
                        // Puede que algún ID no exista: ignóralo o regístralo
                        System.out.println("Error getting product " + id + ": " + ex.getMessage());
                    }
                }
            }

        }catch ( HttpClientErrorException ex){
            System.out.println("Error getting similar ids: " + ex.getMessage());
        }

        

        return result;
    }
}
