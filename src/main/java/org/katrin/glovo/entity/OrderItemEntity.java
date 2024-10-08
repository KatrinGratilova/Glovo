package org.katrin.glovo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "order_items", schema = "public")
public class OrderItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id", nullable = false)
    private int id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity order;

    @NotNull
    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    @Min(value = 0, message = "Price cannot be negative.")
    @NotNull(message = "Price is required")
    @Column(nullable = false, columnDefinition = "SMALLINT CHECK (price >= 0)")
    private double price;

    @Min(value = 1, message = "Quantity cannot be lower than 0.")
    @NotNull(message = "Quantity is required.")
    @Column(nullable = false, columnDefinition = "SMALLINT CHECK (quantity > 0)")
    private int quantity;
}
