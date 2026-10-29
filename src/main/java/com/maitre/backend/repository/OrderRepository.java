package com.maitre.backend.repository;

import com.maitre.backend.entity.Order;
import com.maitre.backend.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Page<Order> findByRestaurantId(Long restaurantId, Pageable pageable);

    Page<Order> findByRestaurantIdAndStatus(Long restaurantId, OrderStatus status, Pageable pageable);
}