package org.katrin.glovo.repository;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.katrin.glovo.entity.OrderEntity;
import org.katrin.glovo.entity.OrderItemEntity;
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

        // Установіть зв'язок між OrderItemEntity та OrderEntity
        orderItemEntity.setOrder(orderEntity);

        // Використовуйте merge замість persist
        if (orderItemEntity.getId() == 0) {
            entityManager.persist(orderItemEntity);
        } else {
            entityManager.merge(orderItemEntity);
        }

        // Додайте збережений OrderItemEntity до OrderEntity
        orderEntity.getItems().add(orderItemEntity);

        // Оновіть OrderEntity (може бути необов'язково, залежить від каскадного типу)
        entityManager.merge(orderEntity);

        return orderEntity;
    }
}
