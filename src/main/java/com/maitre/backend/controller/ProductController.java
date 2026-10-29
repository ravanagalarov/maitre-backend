package com.maitre.backend.controller;

import com.maitre.backend.dto.request.CreateProductRequest;
import com.maitre.backend.dto.request.UpdateProductRequest;
import com.maitre.backend.dto.response.ProductResponse;
import com.maitre.backend.dto.response.TopProductResponse;
import com.maitre.backend.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/restaurants/{restaurantId}/products")
    public ResponseEntity<ProductResponse> createProduct(
            @PathVariable Long restaurantId,
            @Valid @RequestBody CreateProductRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productService.createProduct(restaurantId, request));
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long productId,
            @Valid @RequestBody UpdateProductRequest request) {
        return ResponseEntity.ok(productService.updateProduct(productId, request));
    }

    @GetMapping("/restaurants/{restaurantId}/products/top-selling")
    public ResponseEntity<List<TopProductResponse>> getTopSellingProducts(
            @PathVariable Long restaurantId) {
        return ResponseEntity.ok(productService.getTopSellingProducts(restaurantId));
    }
}