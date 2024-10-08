package org.katrin.glovo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.katrin.glovo.dto.OrderStatus;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "orders", schema = "public")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", nullable = false)
    private int id;

    @NotNull
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn
    private UserEntity client;

    @JsonProperty("status")
    @Column(nullable = false)
    private OrderStatus status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "created_at", nullable = false)
    @ColumnDefault("CURRENT_DATE")
    private LocalDateTime createdAt;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order", orphanRemoval = true, fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn(name = "order_id")
    private List<OrderItemEntity> items = new ArrayList<>();

    @PrePersist
    public void onCreate() {
        createdAt = Optional.ofNullable(createdAt).orElse(LocalDateTime.now());
        status = Optional.ofNullable(status).orElse(OrderStatus.IN_PROCESSING);
    }
}