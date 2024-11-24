package com.challenge.catalogue.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record PostProduct(@NotBlank String name, @NotNull Double price, @NotNull LocalDateTime createdAt, LocalDateTime updatedAt) {

}
