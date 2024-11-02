package org.katrin.glovo.repository;

import org.katrin.glovo.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer>{
    Optional<List<ProductEntity>> findByNameAndCountry(@NotNull String name, String country);
}
