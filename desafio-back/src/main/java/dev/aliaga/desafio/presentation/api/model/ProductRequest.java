package dev.aliaga.desafio.presentation.api.model;

import dev.aliaga.desafio.domain.model.ProductDom;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductRequest {
    private Integer id;
    private String _id;
    private String brand;
    private String description;
    private String image;
    private Long price;

    public static ProductDom toDomain(ProductRequest productRequest) {
        return ProductDom.builder()
                .id(productRequest.getId())
                .brand(productRequest.getBrand())
                .description(productRequest.getDescription())
                .image(productRequest.getImage())
                .price(productRequest.getPrice())
                .build();
    }
}
