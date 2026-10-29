package com.maitre.backend.dto.request;

import com.maitre.backend.enums.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateOrderStatusRequest {

    @NotNull(message = "Status qeyd olunmalıdır")
    private OrderStatus status;
}