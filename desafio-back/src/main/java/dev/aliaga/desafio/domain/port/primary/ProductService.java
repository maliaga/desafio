package dev.aliaga.desafio.domain.port.primary;

import dev.aliaga.desafio.presentation.api.model.ProductResponse;

import java.util.List;

public interface ProductService {
    List<ProductResponse> getAll();
    ProductResponse getById(String id);
}
