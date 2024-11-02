package org.katrin.glovo.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor

@Repository
public class CustomProductRepositoryImpl implements CustomProductRepository {
    private final EntityManager entityManager;

    @Override
    @Transactional
    public boolean existsByNameAndCountry(String name, String country) {
        // JPQL запрос для проверки существования продукта с указанными именем и страной
        String jpql = "SELECT COUNT(p) FROM ProductEntity p WHERE p.name = :name AND p.country = :country";

        TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
        query.setParameter("name", name);
        query.setParameter("country", country);

        Long count = query.getSingleResult();
        return count > 0; // Если count больше 0, значит продукт существует
    }
}
