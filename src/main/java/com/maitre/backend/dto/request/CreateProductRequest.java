package com.maitre.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateProductRequest {

    @NotBlank(message = "Məhsul adı boş ola bilməz")
    private String name;

    @NotBlank(message = "Kateqoriya boş ola bilməz")
    private String category;

    @NotNull(message = "Qiymət qeyd olunmalıdır")
    @Positive(message = "Qiymət müsbət olmalıdır")
    private BigDecimal price;
}