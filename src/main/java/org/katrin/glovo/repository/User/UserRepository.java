package org.katrin.glovo.repository.User;

import org.katrin.glovo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer>, UserRepositoryCustom {
    UserEntity findByEmail(String email);
}
