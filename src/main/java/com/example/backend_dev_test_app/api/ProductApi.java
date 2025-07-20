package com.example.backend_dev_test_app.api;

import com.example.backend_dev_test_app.models.Product;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ProductApi {
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful operation",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Product.class),
                            examples = @ExampleObject(
                                    value = "[{\"id\":\"1\",\"name\":\"Dress\",\"price\":39.99,\"availability\":true}]"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found or no similar products found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = com.example.backend_dev_test_app.models.Error.class),
                            examples = {
                                    @ExampleObject(
                                            name = "ProductNotFound",
                                            description = "Indicates that the requested product ID does not exist in the system.",
                                            value = """
                    {
                      "message": "Product {id} not found.",
                      "error": "Not Found",
                      "status": 404,
                      "date": "2025-07-20T07:52:16.613+00:00"
                    }
                    """
                                    ),
                                    @ExampleObject(
                                            name = "NoSimilarProducts",
                                            description = "Indicates that no similar products are available for the given product ID.",
                                            value = """
                    {
                      "message": "No similar products found for product {id}.",
                      "error": "Not Found",
                      "status": 404,
                      "date": "2025-07-20T07:53:20.123+00:00"
                    }
                    """
                                    ),
                                    @ExampleObject(
                                            name = "SimilarProductNotFound",
                                            description = "Indicates that one of the products identified as similar was not found.",
                                            value = """
                    {
                      "message": "Similar product {id} not found.",
                      "error": "Not Found",
                      "status": 404,
                      "date": "2025-07-20T07:54:00.000+00:00"
                    }
                    """
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "502",
                    description = "External service error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = com.example.backend_dev_test_app.models.Error.class),
                            examples = {
                                    @ExampleObject(
                                            name = "ErrorFetchingSimilarIds",
                                            description = "Occurs when the service fails to retrieve the list of similar product IDs from the external service.",
                                            value = """
                    {
                      "message": "Error fetching similar product IDs for product {id}",
                      "error": "Bad Gateway",
                      "status": 502,
                      "date": "2025-07-20T07:55:30.500+00:00"
                    }
                    """
                                    ),
                                    @ExampleObject(
                                            name = "ErrorFetchingProductDetails",
                                            description = "Occurs when the service fails to retrieve the details of a similar product from the external service.",
                                            value = """
                    {
                      "message": "Error fetching product details for id: {id}",
                      "error": "Bad Gateway",
                      "status": 502,
                      "date": "2025-07-20T07:56:10.250+00:00"
                    }
                    """
                                    )
                            }
                    )
            )
    })
    ResponseEntity<List<Product>> getSimilarProducts(@PathVariable String productId);
}
