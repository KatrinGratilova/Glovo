package org.katrin.glovo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.katrin.glovo.dto.OrderStatus;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "order")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", nullable = false)
    private int id;

    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @JsonProperty("status")
    @Column(columnDefinition = "IN_PROCESSING")
    private OrderStatus status;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "checkout_date", nullable = false)
    @ColumnDefault("CURRENT_DATE")
    @CreationTimestamp
    private LocalDate checkoutDate;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private List<OrderItemEntity> items;
}