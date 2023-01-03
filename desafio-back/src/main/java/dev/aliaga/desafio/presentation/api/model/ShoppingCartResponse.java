package dev.aliaga.desafio.presentation.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.aliaga.desafio.infrastructure.adapters.repositories.entity.ShoppingCartEntity;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ShoppingCartResponse {
    @JsonProperty(value = "id-shopping-cart", index = 0)
    private String idCart;
    @JsonProperty("applied-discount")
    private DiscountResponse discount;
    private Long subtotal;
    private Long total;
    private List<ProductResponse> products;
    @JsonProperty("best-discount")
    private DiscountResponse bestDiscount;

    public static ShoppingCartResponse from(ShoppingCartEntity shoppingCart) {
        return ShoppingCartResponse.builder()
                .idCart(shoppingCart.getIdCart())
                .discount(DiscountResponse.from(shoppingCart.getDiscount()))
                .subtotal(shoppingCart.getSubtotal())
                .total(shoppingCart.getTotal())
                .products(shoppingCart.getProducts().stream().map(ProductResponse::from).collect(java.util.stream.Collectors.toList()))
                .bestDiscount(DiscountResponse.from(shoppingCart.getBestDiscount()))
                .build();
    }
}
