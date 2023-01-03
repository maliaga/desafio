package dev.aliaga.desafio.presentation.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dev.aliaga.desafio.infrastructure.adapters.repositories.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class ProductResponse {
    private Integer id;
    private String _id;
    private String brand;
    private String description;
    private String image;
    private Long price;

    public static ProductResponse from(ProductEntity productEntity) {
        return ProductResponse.builder()
                .id(productEntity.getId())
                ._id(productEntity.get_id())
                .brand(productEntity.getBrand())
                .description(productEntity.getDescription())
                .image(productEntity.getImage())
                .price(productEntity.getPrice())
                .build();
    }
}
