package org.katrin.glovo.repository;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.katrin.glovo.entity.OrderEntity;
import org.katrin.glovo.entity.OrderItemEntity;
import org.katrin.glovo.entity.ProductEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Repository
public class OrderRepositoryCustomImpl implements OrderRepositoryCustom {
    private final EntityManager entityManager;

    @Override
    @Transactional
    public OrderEntity addItem(int orderId, OrderItemEntity orderItemEntity) {
        OrderEntity orderEntity = entityManager.find(OrderEntity.class, orderId);
        if (orderEntity == null) {
            throw new IllegalArgumentException("Order not found for id: " + orderId);
        }
        ProductEntity productEntity = entityManager.find(ProductEntity.class, orderItemEntity.getProduct().getId());
        if (productEntity == null) {
            throw new IllegalArgumentException("Product not found for id: " + orderItemEntity.getProduct().getId());
        }

        orderItemEntity.setProduct(productEntity);
        orderItemEntity.setOrder(orderEntity);
        orderEntity.getItems().add(orderItemEntity);

        entityManager.merge(orderEntity);

        return entityManager.find(OrderEntity.class,orderEntity.getId());
    }
}
