package dev.aliaga.desafio.infrastructure.adapters.repositories;

import dev.aliaga.desafio.infrastructure.adapters.repositories.entity.ShoppingCartEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends MongoRepository<ShoppingCartEntity, String> {
    Optional<ShoppingCartEntity> findByIdCart(String idCart);
}
