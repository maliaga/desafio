package dev.aliaga.desafio.infrastructure.adapters.repositories;

import dev.aliaga.desafio.infrastructure.adapters.repositories.entity.DiscountEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiscountRepository extends MongoRepository<DiscountEntity, String> {
    Optional<DiscountEntity> findByBrand(String brand);
}
