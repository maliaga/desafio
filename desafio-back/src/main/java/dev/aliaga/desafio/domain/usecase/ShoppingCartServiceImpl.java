package dev.aliaga.desafio.domain.usecase;

import dev.aliaga.desafio.domain.model.DiscountDom;
import dev.aliaga.desafio.domain.model.ProductDom;
import dev.aliaga.desafio.domain.model.ShoppingCartDom;
import dev.aliaga.desafio.domain.port.secondary.DiscountPort;
import dev.aliaga.desafio.domain.port.secondary.ProductPort;
import dev.aliaga.desafio.domain.port.secondary.ShoppingCartPort;
import dev.aliaga.desafio.domain.port.primary.ShoppingCartService;
import dev.aliaga.desafio.infrastructure.adapters.repositories.entity.DiscountEntity;
import dev.aliaga.desafio.infrastructure.adapters.repositories.entity.ProductEntity;
import dev.aliaga.desafio.infrastructure.adapters.repositories.entity.ShoppingCartEntity;
import dev.aliaga.desafio.presentation.api.model.ShoppingCartResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {

    public static final int ZERO = 0;

    private final ProductPort productPort;
    private final ShoppingCartPort shoppingCartPort;
    private final DiscountPort discountPort;

    /**
     * Create a new shopping cart with the given products
     * @param shoppingCart the shopping cart
     * @return the shopping cart
     */
    @Override
    public ShoppingCartResponse create(ShoppingCartDom shoppingCart) {
        ProductEntity product = productPort.findById(shoppingCart.getProducts().get(ZERO).getId())
                                           .orElseThrow(() -> new RuntimeException("Product not found"));
        shoppingCart.setProducts(List.of(ProductDom.builder()
                                                   ._id(product.get_id())
                                                   .id(product.getId())
                                                   .brand(product.getBrand())
                                                   .description(product.getDescription())
                                                   .image(product.getImage())
                                                   .price(product.getPrice())
                                                   .build()));
        shoppingCart.setSubtotal(product.getPrice());
        shoppingCart.setTotal(product.getPrice());
        shoppingCart.setBestDiscount(getMaxDiscountByDom(shoppingCart.getProducts()));
        ShoppingCartEntity cart = shoppingCartPort.save(ShoppingCartEntity.from(shoppingCart));
        log.info("create Shopping cart {}", cart);
        return ShoppingCartResponse.from(cart);
    }

    /**
     * Add a product to the shopping cart
     * @param shoppingCart the shopping cart
     * @return the shopping cart
     */
    @Override
    public ShoppingCartResponse addProductToCart(ShoppingCartDom shoppingCart) {
        log.info("Adding product to cart {}", shoppingCart);

        ShoppingCartEntity cart = getShoppingCartEntity(shoppingCart);

        List<ProductEntity> products = new ArrayList<>(cart.getProducts());
        products.add(getProductEntity(shoppingCart));
        return buildShoppingCartResponse(cart, products);
    }

    /**
     * Delete a product from the shopping cart
     * @param shoppingCart the shopping cart
     * @return the shopping cart
     */
    @Override
    public ShoppingCartResponse removeProductToCart(ShoppingCartDom shoppingCart) {
        log.info("Removing product to cart {}", shoppingCart);
        ShoppingCartEntity cart = getShoppingCartEntity(shoppingCart);

        List<ProductEntity> products = new ArrayList<>(cart.getProducts());
        products.stream()
                .filter(p -> p.getId().equals(shoppingCart.getProducts().get(ZERO).getId()))
                .findFirst()
                .ifPresent(products::remove);
        return buildShoppingCartResponse(cart, products);
    }

    /**
     * Build shopping cart response
     * @param cart the shopping cart
     * @param products the products
     * @return the shopping cart response
     */
    private ShoppingCartResponse buildShoppingCartResponse(ShoppingCartEntity cart,
                                                           List<ProductEntity> products) {
        cart.setProducts(products);
        cart.setBestDiscount(calculateBestDiscount(cart.getProducts()));
        cart.setSubtotal(calculateSubTotal(cart.getProducts()));
        cart.setDiscount(calculateAppliedDiscount(cart.getProducts()));
        // if the best discount is same as applied discount (brand), then the best discount is null
        if (null != cart.getBestDiscount() && null != cart.getBestDiscount().getBrand()) {
            cart.setBestDiscount(
                    cart.getBestDiscount().getBrand().equals(cart.getDiscount().getBrand()) ?
                            DiscountEntity.builder().build():
                            cart.getBestDiscount());
        }
        cart.setTotal(calculateTotal(cart.getSubtotal(), cart.getDiscount().getDiscount()));

        return ShoppingCartResponse.from(saveShoppingCartEntity(cart));
    }


    /**
     * Get the max discount by products
     * @param products the products
     * @return the max discount {@link DiscountDom}
     */
    private DiscountDom getMaxDiscountByDom(List<ProductDom> products) {
        DiscountDom discount =
                products.stream()
                        .map(product -> getDiscountByBrand(product.getBrand()))
                        .max(Comparator.comparing(DiscountDom::getDiscount))
                        .orElse(DiscountDom.builder().discount(0L).build());
        log.info("getMaxDiscount {}", discount);
        return discount;
    }

    /**
     * Get the discount by brand
     * @param brand the brand
     * @return the discount {@link DiscountDom}
     */
    private DiscountDom getDiscountByBrand(String brand) {
        DiscountEntity discount =
                discountPort.findByBrand(brand).orElseThrow(() -> new RuntimeException("Discount not found"));

        return DiscountDom.builder()
                          ._id(discount.get_id())
                          .brand(discount.getBrand())
                          .threshold(discount.getThreshold())
                          .discount(discount.getDiscount())
                          .build();

    }

    /**
     * Get the product by brand
     * @param brand the brand
     * @return the discount {@link DiscountEntity}
     */
    private DiscountEntity getDiscountByBrandToEntity(String brand) {
        DiscountEntity discount =
                discountPort.findByBrand(brand).orElseThrow(() -> new RuntimeException("Discount not found"));
        log.info("getDiscountByBrandToEntity {}", discount);

        return discount;

    }

    /**
     * Calculate the best discount from the products
     * @param products  the products
     * @return the best discount {@link DiscountEntity}
     */
    private DiscountEntity calculateBestDiscount(List<ProductEntity> products) {
        DiscountEntity discount =
                products.stream()
                        .map(product -> getDiscountByBrandToEntity(product.getBrand()))
                        .max(Comparator.comparing(DiscountEntity::getDiscount))
                        .orElse(DiscountEntity.builder().discount(0L).build());
        log.info("getMaxDiscount {}", discount);
        return discount;
    }


    /**
     * Calculate the subtotal from the products
     * @param cart the cart
     * @return the subtotal
     */
    private long calculateSubTotal(List<ProductEntity> cart) {
        return cart.stream().mapToLong(ProductEntity::getPrice).sum();
    }

    /**
     * Save the shopping cart
     * @param cart the cart
     * @return the shopping cart
     */
    private ShoppingCartEntity saveShoppingCartEntity(ShoppingCartEntity cart) {
        ShoppingCartEntity shoppingCartEntity = shoppingCartPort.save(cart);
        log.info("Shopping cart {}", shoppingCartEntity);
        return shoppingCartEntity;
    }

    /**
     * Get cart by idCart
     * @param shoppingCart the shopping cart
     * @return the shopping cart
     */
    private ShoppingCartEntity getShoppingCartEntity(ShoppingCartDom shoppingCart) {
        return shoppingCartPort.findByIdCart(shoppingCart.getIdCart())
                               .orElseThrow(() -> new RuntimeException(
                                             "Shopping cart not found"));
    }

    /**
     * Get product by idProduct
     * @param shoppingCart the shopping cart
     * @return the product
     */
    private ProductEntity getProductEntity(ShoppingCartDom shoppingCart) {
        return productPort.findById(shoppingCart.getProducts().get(ZERO).getId())
                          .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    /**
     * Calculate the total from the shopping cart
     * @param subtotal the subtotal
     * @param discount the discount
     * @return the total
     */
    private Long calculateTotal(Long subtotal,
                                Long discount) {
        Long total =  subtotal - discount;
        log.info("calculateDiscount {}", total);
        return total;
    }

    /**
     * Calculate the applied discount from the shopping cart
     * @param products the products
     * @return the discount {@link DiscountEntity}
     */
    private DiscountEntity calculateAppliedDiscount(List<ProductEntity> products) {
        Map<String, List<ProductEntity>> discountsByBrand = getDiscountsByBrand(products);
        // Show console the discounts by brand
        discountsByBrand.forEach((key, value) -> log.info("Brand {} with discount {}", key,
                value.get(ZERO).getBrand()));
        // sum prices by brand
        Map<String, Long> sumByBrand = getSumByBrand(discountsByBrand);
        // Show console the sum by brand
        sumByBrand.forEach((key, value) -> log.info("Brand {} with sum {}", key, value));

        // filter sum by brand with threshold discount
        Map<String, Long> filteredDiscountThreshold = getFilteredDiscountThreshold(sumByBrand);
        // Show console the filtered sum by brand
        filteredDiscountThreshold.forEach((key, value) -> log.info("Brand {} with filtered sum {}", key, value));
        //get max discount by Applying threshold filteredSumByBrand
        return getMaxDiscountByThreshold(filteredDiscountThreshold);
    }

    /**
     * Get max discount by threshold
     * @param filteredSumByBrand the filtered sum by brand
     * @return the discount {@link DiscountEntity}
     */
    private DiscountEntity getMaxDiscountByThreshold(Map<String, Long> filteredSumByBrand) {
        DiscountEntity discount =
                filteredSumByBrand.entrySet()
                                  .stream()
                                  .max(Comparator.comparing(Map.Entry::getValue))
                                  .map(Map.Entry::getKey)
                                  .map(this::getDiscountByBrandToEntity)
                                  .stream().max(Comparator.comparing(DiscountEntity::getDiscount))
                                  .orElse(DiscountEntity.builder().discount(0L).build());
        log.info("getMaxDiscountBySumByBrand {}", discount);
        return discount;
    }

    /**
     * Get filtered sum by brand with threshold discount
     * @param sumByBrand the sum by brand
     * @return the filtered sum by brand
     */
    private Map<String, Long> getFilteredDiscountThreshold(Map<String, Long> sumByBrand) {
        return sumByBrand.entrySet().stream()
                         .filter(entry -> entry.getValue() >= getDiscountByBrandToEntity(entry.getKey()).getThreshold())
                         .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * Get sum by brand
     * @param discountsByBrand the discounts by brand
     * @return the sum by brand
     */
    private Map<String, Long> getSumByBrand(Map<String, List<ProductEntity>> discountsByBrand) {
        Map<String, Long> sumByBrand = new HashMap<>();
        discountsByBrand.forEach((key, value) -> sumByBrand.put(key, calculateSubTotal(value)));
        return sumByBrand;
    }

    /**
     * Group products by brand
     * @param products the products
     * @return the products by brand
     */
    public Map<String, List<ProductEntity>> getDiscountsByBrand(List<ProductEntity> products) {
        Map<String, List<ProductEntity>> productsByBrand = products.stream().collect(Collectors.groupingBy(ProductEntity::getBrand));
        log.info("getDiscountsByBrand {}", productsByBrand);
        return productsByBrand;
    }
}
