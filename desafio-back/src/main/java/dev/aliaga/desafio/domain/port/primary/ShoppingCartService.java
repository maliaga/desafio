package dev.aliaga.desafio.domain.port.primary;

import dev.aliaga.desafio.domain.model.ShoppingCartDom;
import dev.aliaga.desafio.presentation.api.model.ShoppingCartResponse;

public interface ShoppingCartService {
    ShoppingCartResponse create(ShoppingCartDom shoppingCart);
    ShoppingCartResponse addProductToCart(ShoppingCartDom shoppingCart);
    ShoppingCartResponse removeProductToCart(ShoppingCartDom shoppingCartDom);
}
