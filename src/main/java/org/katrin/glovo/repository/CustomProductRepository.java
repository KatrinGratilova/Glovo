package org.katrin.glovo.repository;

import javax.validation.constraints.NotNull;

public interface CustomProductRepository {
    boolean existsByNameAndCountry(@NotNull String name, String country);
}
