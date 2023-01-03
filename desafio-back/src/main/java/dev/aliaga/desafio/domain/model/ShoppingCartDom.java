package dev.aliaga.desafio.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartDom {
    private String idCart;
    private DiscountDom discount;
    private Long subtotal;
    private Long total;
    private List<ProductDom> products;
    private DiscountDom bestDiscount;
}
