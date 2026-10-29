package com.maitre.backend.dto.request;

import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UpdateProductRequest {

    @Positive(message = "Qiymət müsbət olmalıdır")
    private BigDecimal price;

    private Boolean isAvailable;
}