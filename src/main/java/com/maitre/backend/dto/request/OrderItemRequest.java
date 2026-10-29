package com.maitre.backend.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemRequest {

    @NotNull(message = "Məhsul ID-si qeyd olunmalıdır")
    private Long productId;

    @NotNull(message = "Məhsul sayı qeyd olunmalıdır")
    @Min(value = 1, message = "Məhsul sayı ən azı 1 olmalıdır")
    private Integer quantity;
}