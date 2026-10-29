package com.maitre.backend.service;

import com.maitre.backend.dto.request.CreateOrderRequest;
import com.maitre.backend.dto.request.UpdateOrderStatusRequest;
import com.maitre.backend.dto.response.OrderResponse;
import com.maitre.backend.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    OrderResponse createOrder(Long restaurantId, CreateOrderRequest request);
    OrderResponse updateOrderStatus(Long orderId, UpdateOrderStatusRequest request);
    Page<OrderResponse> getOrders(Long restaurantId, OrderStatus status, Pageable pageable);
}