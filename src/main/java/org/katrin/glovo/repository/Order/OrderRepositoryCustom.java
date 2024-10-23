package org.katrin.glovo.repository.Order;

import org.katrin.glovo.entity.OrderEntity;
import org.katrin.glovo.entity.OrderItemEntity;
import org.katrin.glovo.exception.InsufficientStockException;

public interface OrderRepositoryCustom {

    OrderEntity updateWithoutItems(OrderEntity order);

    OrderEntity addItem(int orderId, OrderItemEntity orderItemEntity) throws InsufficientStockException;
}
