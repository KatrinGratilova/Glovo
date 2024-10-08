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

    @Min(0)
    @NotNull
    @Column(nullable = false, columnDefinition = "SMALLINT CHECK (price >= 0)")
    private double price;

    @Column(nullable = false, columnDefinition = "SMALLINT CHECK (quantity > 0)")
    @Min(1)
    private int quantity;
}
