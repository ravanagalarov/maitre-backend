package com.maitre.backend.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateOrderRequest {

    @NotEmpty(message = "Sifarişdə ən azı bir məhsul olmalıdır")
    @Valid
    private List<OrderItemRequest> items;
}