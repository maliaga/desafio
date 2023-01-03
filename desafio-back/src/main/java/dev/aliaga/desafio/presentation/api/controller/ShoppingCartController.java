package dev.aliaga.desafio.presentation.api.controller;

import dev.aliaga.desafio.domain.model.ShoppingCartDom;
import dev.aliaga.desafio.domain.port.primary.ShoppingCartService;
import dev.aliaga.desafio.presentation.api.model.ProductRequest;
import dev.aliaga.desafio.presentation.api.model.ShoppingCartProductRequest;
import dev.aliaga.desafio.presentation.api.model.ShoppingCartRequest;
import dev.aliaga.desafio.presentation.api.model.ShoppingCartResponse;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Api(tags = "Shopping Cart Methods")
@CrossOrigin(origins = "*", methods= {RequestMethod.POST, RequestMethod.DELETE})
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    @PostMapping("/cart/product/{idProduct}")
    public ResponseEntity<ShoppingCartResponse> createCart(@PathVariable("idProduct") Integer idProduct) {
        ShoppingCartDom shoppingCartDom = ShoppingCartRequest.from(ProductRequest.builder()
                                                                                 .id(idProduct)
                                                                                 .build());
        ShoppingCartResponse cartResponse = shoppingCartService.create(shoppingCartDom);

        log.info("Creating cart: {}", cartResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(cartResponse);
    }


    @PostMapping("/cart/{idCart}/product/{idProduct}")
    public ResponseEntity<ShoppingCartResponse> addProductToCart(@PathVariable("idCart") String idCart,
                                                                 @PathVariable("idProduct") Integer idProduct) {
        ShoppingCartDom shoppingCartDom = ShoppingCartProductRequest.toDomain(idCart, ProductRequest.builder()
                                                                                                    .id(idProduct)
                                                                                                    .build());
        ShoppingCartResponse cartResponse = shoppingCartService.addProductToCart(shoppingCartDom);

        log.info("Adding product to cart: {}", cartResponse);
        return ResponseEntity.ok().body(cartResponse);
    }

    @DeleteMapping("/cart/{idCart}/product/{idProduct}")
    public ResponseEntity<ShoppingCartResponse> removeProductToCart(@PathVariable("idCart") String idCart,
                                                                    @PathVariable("idProduct") Integer idProduct) {

        ShoppingCartDom shoppingCartDom = ShoppingCartProductRequest.toDomain(idCart, ProductRequest.builder()
                                                                                                    .id(idProduct)
                                                                                                    .build());
        ShoppingCartResponse cartResponse = shoppingCartService.removeProductToCart(shoppingCartDom);

        log.info("Removing product to cart: {}", cartResponse);
        return ResponseEntity.ok().body(cartResponse);
    }
}
