package org.katrin.glovo.repository.Order;

import org.katrin.glovo.entity.OrderEntity;
import org.katrin.glovo.entity.OrderItemEntity;

public interface OrderRepositoryCustom {

    OrderEntity updateWithoutItems(OrderEntity order);

    OrderEntity addItem(int orderId, OrderItemEntity orderItemEntity);
}
