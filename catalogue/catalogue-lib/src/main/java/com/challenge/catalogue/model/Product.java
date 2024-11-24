package com.challenge.catalogue.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder
public record Product(@NotNull UUID id, @NotBlank String name, @NotNull Double price, @NotNull LocalDateTime createdAt, LocalDateTime updatedAt) {

}
