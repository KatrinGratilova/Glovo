package org.katrin.glovo.repository.User;

import org.katrin.glovo.entity.OrderEntity;
import org.katrin.glovo.entity.UserEntity;

public interface UserRepositoryCustom {
    UserEntity updateWithoutOrders(UserEntity userEntity);

    UserEntity addOrder(int userId, OrderEntity orderEntity);
}
