package com.challange.orders.model;

import java.util.UUID;
import lombok.Builder;

@Builder
public record OrderProduct(UUID id, UUID productId) {

}
