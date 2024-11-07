package org.katrin.glovo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.Optional;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "products", schema = "public", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "country"}, name = "products_name_country_key")
})
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false)
    private int id;

    @NotNull(message = "Product name is required.")
    @Length(min = 1, max = 200, message = "Product name is required. It can`t be longer than 200 characters.")
    @Column(nullable = false)
    private String name;

    @Min(value = 0, message = "Stock quantity cannot be negative.")
    @Max(value = 10000, message = "Stock quantity cannot be bigger than 10000.")
    @NotNull(message = "Stock quantity is required.")
    @Column(name = "stock_quantity", columnDefinition = "SMALLINT CHECK (stock_quantity BETWEEN 0 AND 10000)", nullable = false)
    private int stockQuantity;

    @NotNull(message = "Country name is required.")
    @Column(nullable = false)
    @Length(min = 3, max = 100, message = "County name must be between 3 and 100 characters.")
    private String country;

    @Min(value = 0, message = "Price cannot be negative.")
    @Max(value = 100000, message = "Stock quantity cannot be bigger than 100000.")
    @NotNull(message = "Price is required.")
    @Column(nullable = false, columnDefinition = "SMALLINT CHECK (price BETWEEN 0 AND 100000)")
    private double price;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {
        country = Optional.ofNullable(country).orElse("Ukraine");
        createdAt = LocalDateTime.now();
    }
}
