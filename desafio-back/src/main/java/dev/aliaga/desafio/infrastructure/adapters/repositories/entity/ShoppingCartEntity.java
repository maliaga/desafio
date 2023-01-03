package dev.aliaga.desafio.infrastructure.adapters.repositories.entity;

import dev.aliaga.desafio.domain.model.DiscountDom;
import dev.aliaga.desafio.domain.model.ShoppingCartDom;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@Builder
@Document(value = "shopping_cart")
public class ShoppingCartEntity {
    @Id
    private String _id;
    @Field("id_shopping_cart")
    private String idCart;
    private DiscountEntity discount;
    private Long subtotal;
    private Long total;
    private List<ProductEntity> products;
    @Field("best_discount")
    private DiscountEntity bestDiscount;

    public static ShoppingCartEntity from(ShoppingCartDom shoppingCartDom) {
        return ShoppingCartEntity.builder()
                .idCart(UUID.randomUUID().toString())
                .discount(shoppingCartDom.getDiscount() == null ? DiscountEntity.builder().build() : DiscountEntity.from(shoppingCartDom.getDiscount()))
                .subtotal(shoppingCartDom.getSubtotal() == null ? 0L : shoppingCartDom.getSubtotal())
                .total(shoppingCartDom.getTotal() == null ? 0L : shoppingCartDom.getTotal())
                .products(shoppingCartDom.getProducts().stream().map(ProductEntity::from).collect(Collectors.toList()))
                .bestDiscount(DiscountEntity.from(shoppingCartDom.getBestDiscount() == null ? DiscountDom.builder().build() :
                        shoppingCartDom.getBestDiscount()))
                .build();
    }
}
