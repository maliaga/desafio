package dev.aliaga.desafio.infrastructure.adapters.repositories;

import dev.aliaga.desafio.infrastructure.adapters.repositories.entity.ProductEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends MongoRepository<ProductEntity, String> {
    Optional<ProductEntity> findById(Integer id);
}
