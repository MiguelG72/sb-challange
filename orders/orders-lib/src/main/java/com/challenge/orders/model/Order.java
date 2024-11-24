package com.challenge.orders.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.Builder;

@Builder
public record Order(@NotNull UUID id, @NotBlank String address,
	@NotNull LocalDateTime createdAt, LocalDateTime updatedAt, @NotNull Double priceWithTax, @NotNull List<OrderProduct> products,
	@NotNull OrderStatus status

) {

}
