package org.katrin.glovo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class OrderItemDto {
    private int id;
    private double price;
    private int quantity;
    private int productId;
    private int orderId;
}
