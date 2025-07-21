package com.example.backend_dev_test_app.services;

import com.example.backend_dev_test_app.exceptions.ExternalServiceException;
import com.example.backend_dev_test_app.exceptions.NotFoundException;
import com.example.backend_dev_test_app.models.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productService = new ProductServiceImpl();
        productService = Mockito.spy(productService);
        doReturn(restTemplate).when(productService).getRestTemplate();
    }

    @Test
    void testGetSimilarProducts_success() {
        String productId = "1";
        String[] similarIds = {"2", "3", "4"};

        Product product2 = new Product();
        product2.setId("2");
        product2.setName("Shirt");
        product2.setPrice(29.99);
        product2.setAvailability(true);

        Product product3 = new Product();
        product3.setId("3");
        product3.setName("Pants");
        product3.setPrice(49.99);
        product3.setAvailability(false);

        when(restTemplate.getForObject(contains("/similarids"), eq(String[].class)))
                .thenReturn(similarIds);

        when(restTemplate.getForObject(contains("/2"), eq(Product.class)))
                .thenReturn(product2);
        when(restTemplate.getForObject(contains("/3"), eq(Product.class)))
                .thenReturn(product3);
        when(restTemplate.getForObject(contains("/4"), eq(Product.class)))
                .thenReturn(product3);

        List<Product> result = productService.getSimilarProducts(productId);

        assertEquals(3, result.size());
        assertEquals("2", result.get(0).getId());
        assertEquals("3", result.get(1).getId());
        assertEquals("4", result.get(2).getId());

    }


    @Test
    void testGetSimilarProducts_productNotFound() {
        when(restTemplate.getForObject(anyString(), eq(String[].class)))
                .thenThrow(HttpClientErrorException.NotFound.class);

        assertThrows(NotFoundException.class, () -> productService.getSimilarProducts("999"));
    }

    @Test
    void testGetSimilarProducts_noSimilarIds() {
        when(restTemplate.getForObject(anyString(), eq(String[].class)))
                .thenReturn(new String[]{});

        assertThrows(NotFoundException.class, () -> productService.getSimilarProducts("4"));
    }

    @Test
    void testGetSimilarProducts_externalErrorOnSimilarIds() {
        when(restTemplate.getForObject(anyString(), eq(String[].class)))
                .thenThrow(new RestClientException("Service unavailable"));

        assertThrows(ExternalServiceException.class, () -> productService.getSimilarProducts("5"));
    }

}
