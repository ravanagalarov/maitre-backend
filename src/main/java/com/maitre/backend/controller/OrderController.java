package com.maitre.backend.controller;

import com.maitre.backend.dto.request.CreateOrderRequest;
import com.maitre.backend.dto.request.UpdateOrderStatusRequest;
import com.maitre.backend.dto.response.OrderResponse;
import com.maitre.backend.enums.OrderStatus;
import com.maitre.backend.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/restaurants/{restaurantId}/orders")
    public ResponseEntity<OrderResponse> createOrder(
            @PathVariable Long restaurantId,
            @Valid @RequestBody CreateOrderRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderService.createOrder(restaurantId, request));
    }

    @PatchMapping("/orders/{orderId}/status")
    public ResponseEntity<OrderResponse> updateOrderStatus(
            @PathVariable Long orderId,
            @Valid @RequestBody UpdateOrderStatusRequest request) {
        return ResponseEntity.ok(orderService.updateOrderStatus(orderId, request));
    }

    @GetMapping("/restaurants/{restaurantId}/orders")
    public ResponseEntity<Page<OrderResponse>> getOrders(
            @PathVariable Long restaurantId,
            @RequestParam(required = false) OrderStatus status,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(orderService.getOrders(restaurantId, status, pageable));
    }
}