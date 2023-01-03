package dev.aliaga.desafio.presentation.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.aliaga.desafio.domain.model.ShoppingCartDom;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ShoppingCartProductRequest {
    @JsonProperty("id-shopping-cart")
    private String idCart;
    private ProductRequest product;

    public static ShoppingCartDom toDomain(String idCart, ProductRequest product) {
        return ShoppingCartDom.builder()
                .idCart(idCart)
                .products(List.of(ProductRequest.toDomain(product)))
                .build();
    }
}
