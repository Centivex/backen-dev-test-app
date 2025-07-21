package com.example.backend_dev_test_app.controllers;

import com.example.backend_dev_test_app.models.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerITest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl(String productId) {
        return "http://localhost:" + port + "/product/" + productId + "/similar";
    }

    @Test
    void testGetSimilarProducts_success() {
        ResponseEntity<Product[]> response = restTemplate.getForEntity(baseUrl("1"), Product[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length > 0);
    }

    @Test
    void testGetSimilarProducts_notFound() {
        ResponseEntity<String> response = restTemplate.getForEntity(baseUrl("nonexistent"), String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody().contains("Product nonexistent not found"));
    }

    @Test
    void testGetSimilarProducts_externalServiceError() {
        ResponseEntity<String> response = restTemplate.getForEntity(baseUrl("5"), String.class);
        assertEquals(HttpStatus.BAD_GATEWAY, response.getStatusCode());
    }
}
