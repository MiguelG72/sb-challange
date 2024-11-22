package com.challange.orders.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import java.util.UUID;

public record PostOrder(@NotBlank String address, @NotEmpty List<UUID> products) {

}
