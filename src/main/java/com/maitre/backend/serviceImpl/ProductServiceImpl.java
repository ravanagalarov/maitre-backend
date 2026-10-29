package com.maitre.backend.serviceImpl;

import com.maitre.backend.dto.request.CreateProductRequest;
import com.maitre.backend.dto.request.UpdateProductRequest;
import com.maitre.backend.dto.response.ProductResponse;
import com.maitre.backend.dto.response.TopProductResponse;
import com.maitre.backend.entity.Product;
import com.maitre.backend.entity.Restaurant;
import com.maitre.backend.enums.OrderStatus;
import com.maitre.backend.exception.ResourceNotFoundException;
import com.maitre.backend.repository.OrderItemRepository;
import com.maitre.backend.repository.ProductRepository;
import com.maitre.backend.repository.RestaurantRepository;
import com.maitre.backend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final RestaurantRepository restaurantRepository;
    private final OrderItemRepository orderItemRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public ProductResponse createProduct(Long restaurantId, CreateProductRequest request) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restoran tapılmadı: ID " + restaurantId));

        Product product = Product.builder()
                .name(request.getName())
                .category(request.getCategory())
                .price(request.getPrice())
                .isAvailable(true)
                .restaurant(restaurant)
                .build();

        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductResponse.class);
    }

    @Override
    @Transactional
    public ProductResponse updateProduct(Long productId, UpdateProductRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Məhsul tapılamdı: ID " + productId));

        if (request.getPrice() != null) {
            product.setPrice(request.getPrice());
        }
        if (request.getIsAvailable() != null) {
            product.setIsAvailable(request.getIsAvailable());
        }

        Product updatedProduct = productRepository.save(product);
        return modelMapper.map(updatedProduct, ProductResponse.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TopProductResponse> getTopSellingProducts(Long restaurantId) {
        if (!restaurantRepository.existsById(restaurantId)) {
            throw new ResourceNotFoundException("Restoran tapılmadı: ID " + restaurantId);
        }

        return orderItemRepository.findTopSellingProducts(
                restaurantId,
                OrderStatus.CANCELLED,
                PageRequest.of(0, 5)
        );
    }
}