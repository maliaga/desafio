package dev.aliaga.desafio.presentation.api.model;

import dev.aliaga.desafio.domain.model.ShoppingCartDom;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ShoppingCartRequest {
    private ProductRequest product;

    public static ShoppingCartDom from(ProductRequest productRequest) {
        return ShoppingCartDom.builder()
                .products(List.of(ProductRequest.toDomain(productRequest)))
                .build();
    }
}
