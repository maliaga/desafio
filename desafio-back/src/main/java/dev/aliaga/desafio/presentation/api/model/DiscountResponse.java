package dev.aliaga.desafio.presentation.api.model;

import dev.aliaga.desafio.infrastructure.adapters.repositories.entity.DiscountEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DiscountResponse {
    private String brand;
    private Long discount;
    private Long threshold;

    public static DiscountResponse from(DiscountEntity discountEntity) {
        return DiscountResponse.builder()
                .brand(discountEntity.getBrand())
                .discount(discountEntity.getDiscount())
                .threshold(discountEntity.getThreshold())
                .build();
    }
}
