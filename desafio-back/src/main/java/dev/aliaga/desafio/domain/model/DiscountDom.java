package dev.aliaga.desafio.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DiscountDom {
    private String _id;
    private String brand;
    private Long threshold;
    private Long discount;
}
