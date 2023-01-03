package dev.aliaga.desafio.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDom {
    private Integer id;
    private String _id;
    private String brand;
    private String description;
    private String image;
    private Long price;
}
