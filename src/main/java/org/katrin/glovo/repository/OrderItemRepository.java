package org.katrin.glovo.repository;

import org.katrin.glovo.entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Integer> {
    List<OrderItemEntity> findByOrderId(int orderId);

}
