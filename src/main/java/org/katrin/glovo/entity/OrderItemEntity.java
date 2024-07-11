package org.katrin.glovo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "order_item")
public class OrderItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id", nullable = false)
    private int id;

    @Column(name = "order_id", nullable = false)
    @ManyToOne(cascade = CascadeType.REFRESH)
    private OrderEntity order;

    @Column(name = "product_id", nullable = false)
    @OneToOne(cascade = CascadeType.REFRESH)
    private ProductEntity product;

    @Column(nullable = false)
    @Min(0)
    private double price;

    @Column(nullable = false)
    @Min(0)
    private int quantity;
}
