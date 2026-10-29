package com.maitre.backend.dto.response;

import java.time.LocalDateTime;

public record ErrorResponse(
        String message,
        LocalDateTime timestamp
) {}