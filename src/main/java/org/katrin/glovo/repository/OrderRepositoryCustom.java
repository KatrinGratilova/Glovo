package org.katrin.glovo.repository;

import org.katrin.glovo.entity.OrderEntity;
import org.katrin.glovo.entity.OrderItemEntity;

public interface OrderRepositoryCustom {
    OrderEntity addItem(int orderId, OrderItemEntity orderItemEntity);
}
