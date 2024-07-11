package org.katrin.glovo.repository;

import org.katrin.glovo.dto.OrderDto;
import org.katrin.glovo.dto.OrderItemDto;
import org.katrin.glovo.entity.OrderEntity;
import org.katrin.glovo.entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {
    OrderEntity addItem(int orderId, OrderItemEntity orderItemEntity);
}
