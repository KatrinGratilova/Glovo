package org.katrin.glovo.repository.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.katrin.glovo.entity.OrderEntity;
import org.katrin.glovo.entity.UserEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor

@Repository
public class UserRepositoryCustomImpl implements UserRepositoryCustom {
    private final EntityManager entityManager;

    @Override
    @Transactional
    public UserEntity updateWithoutOrders(UserEntity userModified) {
        UserEntity userEntity = entityManager.find(userModified.getClass(), userModified.getId());
        if (userEntity == null)
            throw new EntityNotFoundException("User not found for id: " + userModified.getId());

        userEntity.setName(userModified.getName());
        //userEntity.setEmail(userModified.getEmail());
        userEntity.setPhoneNumber(userModified.getPhoneNumber());
        userEntity.setPassword(userModified.getPassword());

        return entityManager.merge(userEntity);
    }

    @Override
    @Transactional
    public UserEntity addOrder(int userId, OrderEntity orderEntity) {
        UserEntity userEntity = entityManager.find(UserEntity.class, userId);
        if (userEntity == null)
            throw new EntityNotFoundException("User not found for id: " + userId);

        orderEntity.setClient(userEntity);
        userEntity.getOrders().add(orderEntity);

        entityManager.merge(userEntity);

        return entityManager.find(UserEntity.class, userEntity.getId());
    }
}
