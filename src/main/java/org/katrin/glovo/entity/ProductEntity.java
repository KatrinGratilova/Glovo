package org.katrin.glovo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Optional;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "products", schema = "public")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false)
    private int id;

    @NotNull
    @Column(nullable = false)
    private String name;

    @Min(value = 0, message = "Stock quantity cannot be negative.")
    @NotNull(message = "Stock quantity is required.")
    @Column(name = "stock_quantity", columnDefinition = "SMALLINT CHECK (stock_quantity >= 0)", nullable = false)
    private int stockQuantity;

    @Column(nullable = false)
    private String country;

    @Min(value = 0, message = "Price cannot be negative.")
    @NotNull(message = "Price is required.")
    @Column(nullable = false, columnDefinition = "SMALLINT CHECK (price >= 0)")
    private double price;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {
        country = Optional.ofNullable(country).orElse("Ukraine");
        createdAt = LocalDateTime.now();
    }
}
