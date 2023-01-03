package dev.aliaga.desafio.infrastructure.adapters.repositories.entity;

import dev.aliaga.desafio.domain.model.ProductDom;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(value = "products")
public class ProductEntity {
    @Id
    private String _id;
    private Integer id;
    private String brand;
    private String description;
    private String image;
    private Long price;

    public static ProductEntity from(ProductDom productDom) {
        return ProductEntity.builder()
                ._id(productDom.get_id())
                .id(productDom.getId())
                .brand(productDom.getBrand())
                .description(productDom.getDescription())
                .image(productDom.getImage())
                .price(productDom.getPrice())
                .build();
    }

    public static ProductDom from(ProductEntity productEntity) {
        return ProductDom.builder()
                ._id(productEntity._id)
                .id(productEntity.id)
                .brand(productEntity.brand)
                .description(productEntity.description)
                .image(productEntity.image)
                .price(productEntity.price)
                .build();
    }
}
