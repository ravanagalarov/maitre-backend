package com.maitre.backend.repository;

import com.maitre.backend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByRestaurantId(Long restaurantId);
}