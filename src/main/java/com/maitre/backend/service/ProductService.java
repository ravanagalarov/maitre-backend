package com.maitre.backend.service;

import com.maitre.backend.dto.request.CreateProductRequest;
import com.maitre.backend.dto.request.UpdateProductRequest;
import com.maitre.backend.dto.response.ProductResponse;
import com.maitre.backend.dto.response.TopProductResponse;

import java.util.List;

public interface ProductService {
    ProductResponse createProduct(Long restaurantId, CreateProductRequest request);
    ProductResponse updateProduct(Long productId, UpdateProductRequest request);
    List<TopProductResponse> getTopSellingProducts(Long restaurantId);
}