package org.katrin.glovo.repository.Order;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.katrin.glovo.entity.OrderEntity;
import org.katrin.glovo.entity.OrderItemEntity;
import org.katrin.glovo.entity.ProductEntity;
import org.katrin.glovo.exception.InsufficientStockException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@AllArgsConstructor
@Repository
public class OrderRepositoryCustomImpl implements OrderRepositoryCustom {
    private final EntityManager entityManager;

    @Override
    @Transactional
    public OrderEntity updateWithoutItems(OrderEntity orderModified) {
        OrderEntity order = entityManager.find(OrderEntity.class, orderModified.getId());
        if (order == null)
            throw new EntityNotFoundException("Order not found.");

        order.setClient(orderModified.getClient());
        order.setStatus(Optional.ofNullable(orderModified.getStatus()).orElse(order.getStatus()));

        order = entityManager.merge(order);

        return order;
    }

    @Override
    @Transactional
    public OrderEntity addItem(int orderId, OrderItemEntity orderItemEntity) throws EntityNotFoundException, InsufficientStockException {
        OrderEntity orderEntity = entityManager.find(OrderEntity.class, orderId);
        if (orderEntity == null)
            throw new EntityNotFoundException("Order not found.");

        ProductEntity productEntity = entityManager.find(ProductEntity.class, orderItemEntity.getProduct().getId());
        if (productEntity == null)
            throw new EntityNotFoundException("Product not found.");

        if (orderItemEntity.getQuantity() > productEntity.getStockQuantity())
            throw new InsufficientStockException("Not enough stock available.");

        orderItemEntity.setProduct(productEntity);
        orderItemEntity.setOrder(orderEntity);
        orderEntity.getItems().add(orderItemEntity);

        entityManager.merge(orderEntity);

        return entityManager.find(OrderEntity.class, orderEntity.getId());
    }
}
