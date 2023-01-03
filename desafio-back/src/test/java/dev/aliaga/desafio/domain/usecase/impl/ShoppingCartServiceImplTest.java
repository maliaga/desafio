package dev.aliaga.desafio.domain.usecase.impl;

import dev.aliaga.desafio.domain.model.ProductDom;
import dev.aliaga.desafio.domain.model.ShoppingCartDom;
import dev.aliaga.desafio.domain.port.secondary.DiscountPort;
import dev.aliaga.desafio.domain.port.secondary.ProductPort;
import dev.aliaga.desafio.domain.port.secondary.ShoppingCartPort;
import dev.aliaga.desafio.domain.usecase.ShoppingCartServiceImpl;
import dev.aliaga.desafio.infrastructure.adapters.repositories.entity.DiscountEntity;
import dev.aliaga.desafio.infrastructure.adapters.repositories.entity.ProductEntity;
import dev.aliaga.desafio.infrastructure.adapters.repositories.entity.ShoppingCartEntity;
import dev.aliaga.desafio.presentation.api.model.ShoppingCartResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class ShoppingCartServiceImplTest {

    public static final String ID_CART = "dc6b5f50-6365-4a86-9caa-f82c43b662ca";
    public static final int ZERO_INDEX = 0;
    public static final int ONE_INDEX = 1;

    @InjectMocks
    private ShoppingCartServiceImpl shoppingCartService;
    @Mock
    private ShoppingCartPort shoppingCartPort;
    @Mock
    public ProductPort productPort;
    @Mock
    public DiscountPort discountPort;

    @Test
    void shouldAddTwoProductToCart() {

        ShoppingCartDom shoppingCartDom = buildShoppingCartDom();
        ShoppingCartEntity shoppingCartEntity = buildShoppingCartEntity();
        ProductEntity productEntity = buildProductEntityMarca6();
        DiscountEntity discountEntityMarca6 =
                buildDiscountEntity("624b943175d8be9230d5381f", "Marca6", 250000L, 26000L);
        DiscountEntity discountEntityMarca1 =
                buildDiscountEntity("624b943175d8be9230d53815", "Marca1", 120000L, 8000L);

        doReturn(Optional.of(shoppingCartEntity)).when(shoppingCartPort).findByIdCart(ID_CART);
        doReturn(Optional.of(productEntity)).when(productPort).findById(18);
        doReturn(Optional.of(discountEntityMarca1)).when(discountPort).findByBrand("Marca1");
        doReturn(Optional.of(discountEntityMarca6)).when(discountPort).findByBrand("Marca6");
        doReturn(shoppingCartEntity).when(shoppingCartPort).save(shoppingCartEntity);

        ShoppingCartResponse cart = shoppingCartService.addProductToCart(shoppingCartDom);

        assertNotNull(cart);
        assertEquals(ID_CART, cart.getIdCart());
        assertEquals(260000L, cart.getSubtotal());
        assertEquals(260000L, cart.getTotal());
        assertEquals(2, cart.getProducts().size());
        assertEquals(1, cart.getProducts().get(ZERO_INDEX).getId());
        assertEquals("Marca1", cart.getProducts().get(ZERO_INDEX).getBrand());
        assertEquals("Televisión 54''", cart.getProducts().get(ZERO_INDEX).getDescription());
        assertEquals("www.lider.cl/catalogo/images/catalogo_no_photo.jpg", cart.getProducts().get(ZERO_INDEX).getImage());
        assertEquals(80000L, cart.getProducts().get(ZERO_INDEX).getPrice());
        assertEquals(18, cart.getProducts().get(ONE_INDEX).getId());
        assertEquals("Marca6", cart.getProducts().get(ONE_INDEX).getBrand());
        assertEquals("Laptop Gamer YPS Bell", cart.getProducts().get(ONE_INDEX).getDescription());
        assertEquals("www.lider.cl/catalogo/images/catalogo_no_photo.jpg", cart.getProducts().get(ONE_INDEX).getImage());
        assertEquals(180000L, cart.getProducts().get(ONE_INDEX).getPrice());

    }

    @Test
    void shouldRemoveOneProductToCart() {

        ShoppingCartDom shoppingCartDom = buildShoppingCartDom();
        ShoppingCartEntity shoppingCartEntity = buildShoppingCart();
        DiscountEntity discountEntityMarca1 =
                buildDiscountEntity("624b943175d8be9230d53815", "Marca1", 120000L, 8000L);

        doReturn(Optional.of(shoppingCartEntity)).when(shoppingCartPort).findByIdCart(ID_CART);
        doReturn(Optional.of(discountEntityMarca1)).when(discountPort).findByBrand("Marca1");
        doReturn(shoppingCartEntity).when(shoppingCartPort).save(shoppingCartEntity);

        ShoppingCartResponse cart = shoppingCartService.removeProductToCart(shoppingCartDom);

        assertNotNull(cart);
        assertEquals(ID_CART, cart.getIdCart());
        assertEquals(80000L, cart.getSubtotal());
        assertEquals(80000L, cart.getTotal());
        assertEquals(1, cart.getProducts().size());
        assertEquals(1, cart.getProducts().get(ZERO_INDEX).getId());
        assertEquals("Marca1", cart.getProducts().get(ZERO_INDEX).getBrand());
        assertEquals("Televisión 54''", cart.getProducts().get(ZERO_INDEX).getDescription());
        assertEquals("www.lider.cl/catalogo/images/catalogo_no_photo.jpg", cart.getProducts().get(ZERO_INDEX).getImage());
        assertEquals(80000L, cart.getProducts().get(ZERO_INDEX).getPrice());

    }

    private DiscountEntity buildDiscountEntity(String _id,
                                               String Marca6,
                                               long threshold,
                                               long discount) {
        return DiscountEntity.builder()
                             ._id(_id)
                             .brand(Marca6)
                             .threshold(threshold)
                             .discount(discount)
                             .build();
    }

    private ProductEntity buildProductEntityMarca6() {
        return ProductEntity.builder()
                            ._id("624b943175d8be9230d537c8")
                            .id(18)
                            .brand("Marca6")
                            .description("Laptop Gamer YPS Bell")
                            .image("www.lider.cl/catalogo/images/catalogo_no_photo.jpg")
                            .price(180000L)
                            .build();
    }

    private ShoppingCartEntity buildShoppingCartEntity() {
        return ShoppingCartEntity.builder()
                                 ._id("624b9496d2e490711a39e34e")
                                 .idCart(ID_CART)
                                 .discount(DiscountEntity.builder()._id(null)
                                                                                          .brand(null).threshold(null)
                                                                                          .discount(null).build())
                                 .subtotal(80000L)
                                 .total(80000L)
                                 .products(List.of(buildProductEntityMarca1()))
                                 .bestDiscount(DiscountEntity.builder()._id(null).brand("Marca1").threshold(120000L).discount(8000L).build())
                                 .build();
    }

    private ShoppingCartEntity buildShoppingCart() {
        return ShoppingCartEntity.builder()
                                 ._id("624b9496d2e490711a39e34e")
                                 .idCart(ID_CART)
                                 .discount(DiscountEntity.builder()._id(null)
                                                         .brand(null).threshold(null)
                                                         .discount(null).build())
                                 .subtotal(80000L)
                                 .total(80000L)
                                 .products(List.of(buildProductEntityMarca6(), buildProductEntityMarca1()))
                                 .bestDiscount(DiscountEntity.builder()._id(null).brand("Marca6").threshold(250000L).discount(26000L).build())
                                 .build();
    }

    private ProductEntity buildProductEntityMarca1() {
        return ProductEntity.builder()
                            ._id("624b943175d8be9230d537a6")
                            .id(1)
                            .brand("Marca1")
                            .description(
                                    "Televisión 54''")
                            .image("www.lider.cl/catalogo/images/catalogo_no_photo.jpg")
                            .price(80000L)
                            .build();
    }

    private ShoppingCartDom buildShoppingCartDom() {
        return ShoppingCartDom.builder()
                              .idCart(ID_CART)
                              .products(List.of(ProductDom.builder().id(18).build()))
                              .build();
    }
}