package com.maitre.backend.serviceImpl;

import com.maitre.backend.dto.request.CreateOrderRequest;
import com.maitre.backend.dto.request.OrderItemRequest;
import com.maitre.backend.dto.request.UpdateOrderStatusRequest;
import com.maitre.backend.dto.response.OrderItemResponse;
import com.maitre.backend.dto.response.OrderResponse;
import com.maitre.backend.entity.Order;
import com.maitre.backend.entity.OrderItem;
import com.maitre.backend.entity.Product;
import com.maitre.backend.entity.Restaurant;
import com.maitre.backend.enums.OrderStatus;
import com.maitre.backend.exception.InvalidOperationException;
import com.maitre.backend.exception.ResourceNotFoundException;
import com.maitre.backend.repository.OrderRepository;
import com.maitre.backend.repository.ProductRepository;
import com.maitre.backend.repository.RestaurantRepository;
import com.maitre.backend.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final RestaurantRepository restaurantRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public OrderResponse createOrder(Long restaurantId, CreateOrderRequest request) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restoran tapılmadı: ID " + restaurantId));

        Order order = Order.builder()
                .restaurant(restaurant)
                .status(OrderStatus.PENDING)
                .totalAmount(BigDecimal.ZERO)
                .items(new ArrayList<>())
                .build();

        BigDecimal totalAmount = BigDecimal.ZERO;

        for (OrderItemRequest itemRequest : request.getItems()) {
            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Məhsul tapılmadı: ID " + itemRequest.getProductId()));

            if (!product.getRestaurant().getId().equals(restaurantId)) {
                throw new InvalidOperationException("Məhsul (ID: " + product.getId() + ") bu restorana aid deyil");
            }

            if (Boolean.FALSE.equals(product.getIsAvailable())) {
                throw new InvalidOperationException("Məhsul hazırda satışda deyil: " + product.getName());
            }

            BigDecimal lineTotal = product.getPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity()));
            totalAmount = totalAmount.add(lineTotal);

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .product(product)
                    .productName(product.getName())
                    .unitPrice(product.getPrice())
                    .quantity(itemRequest.getQuantity())
                    .lineTotal(lineTotal)
                    .build();

            order.getItems().add(orderItem);
        }

        order.setTotalAmount(totalAmount);
        Order savedOrder = orderRepository.save(order);

        return mapToOrderResponse(savedOrder);
    }

    @Override
    @Transactional
    public OrderResponse updateOrderStatus(Long orderId, UpdateOrderStatusRequest request) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Sifariş tapılmadı: ID " + orderId));

        OrderStatus currentStatus = order.getStatus();
        OrderStatus newStatus = request.getStatus();

        validateStatusTransition(currentStatus, newStatus);

        order.setStatus(newStatus);
        Order updatedOrder = orderRepository.save(order);

        return mapToOrderResponse(updatedOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderResponse> getOrders(Long restaurantId, OrderStatus status, Pageable pageable) {
        if (!restaurantRepository.existsById(restaurantId)) {
            throw new ResourceNotFoundException("Restoran tapılmadı: ID " + restaurantId);
        }

        Page<Order> orders;
        if (status != null) {
            orders = orderRepository.findByRestaurantIdAndStatus(restaurantId, status, pageable);
        } else {
            orders = orderRepository.findByRestaurantId(restaurantId, pageable);
        }

        return orders.map(this::mapToOrderResponse);
    }

    private void validateStatusTransition(OrderStatus current, OrderStatus next) {
        if (current == next) {
            return;
        }

        boolean isValid = switch (current) {
            case PENDING -> next == OrderStatus.PREPARING || next == OrderStatus.CANCELLED;
            case PREPARING -> next == OrderStatus.READY;
            case READY -> next == OrderStatus.COMPLETED;
            case COMPLETED, CANCELLED -> false;
        };

        if (!isValid) {
            throw new InvalidOperationException(
                    String.format("Sifariş statusu '%s' vəziyyətindən '%s' vəziyyətinə dəyişdirilə bilməz", current, next)
            );
        }
    }

    private OrderResponse mapToOrderResponse(Order order) {
        List<OrderItemResponse> itemResponses = order.getItems().stream()
                .map(item -> OrderItemResponse.builder()
                        .id(item.getId())
                        .productId(item.getProduct().getId())
                        .productName(item.getProductName())
                        .unitPrice(item.getUnitPrice())
                        .quantity(item.getQuantity())
                        .lineTotal(item.getLineTotal())
                        .build())
                .toList();

        return OrderResponse.builder()
                .id(order.getId())
                .restaurantId(order.getRestaurant().getId())
                .status(order.getStatus())
                .totalAmount(order.getTotalAmount())
                .createdAt(order.getCreatedAt())
                .items(itemResponses)
                .build();
    }
}