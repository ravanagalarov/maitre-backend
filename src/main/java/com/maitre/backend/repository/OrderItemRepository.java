package com.maitre.backend.repository;

import com.maitre.backend.dto.response.TopProductResponse;
import com.maitre.backend.entity.OrderItem;
import com.maitre.backend.enums.OrderStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Query("SELECT new com.maitre.backend.dto.response.TopProductResponse(oi.product.id, oi.productName, SUM(oi.quantity)) " +
            "FROM OrderItem oi " +
            "WHERE oi.order.restaurant.id = :restaurantId " +
            "AND oi.order.status <> :excludedStatus " +
            "GROUP BY oi.product.id, oi.productName " +
            "ORDER BY SUM(oi.quantity) DESC")
    List<TopProductResponse> findTopSellingProducts(
            @Param("restaurantId") Long restaurantId,
            @Param("excludedStatus") OrderStatus excludedStatus,
            Pageable pageable
    );
}