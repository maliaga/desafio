package dev.aliaga.desafio.infrastructure.adapters.repositories.entity;

import dev.aliaga.desafio.domain.model.DiscountDom;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(value = "discounts")
public class DiscountEntity {
    @Id
    private String _id;
    private String brand;
    private Long threshold;
    private Long discount;

    public static DiscountEntity from(DiscountDom bestDiscount) {
        return DiscountEntity.builder()
                .brand(bestDiscount.getBrand())
                .threshold(bestDiscount.getThreshold())
                .discount(bestDiscount.getDiscount())
                .build();
    }
}
