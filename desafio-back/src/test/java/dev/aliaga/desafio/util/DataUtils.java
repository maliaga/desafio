package dev.aliaga.desafio.util;

import dev.aliaga.desafio.presentation.api.model.ProductResponse;

public class DataUtils {

    public static ProductResponse buildProductEntityMarca6() {
        return ProductResponse.builder()
                            ._id("624b943175d8be9230d537c8")
                            .id(18)
                            .brand("Marca6")
                            .description("Laptop Gamer YPS Bell")
                            .image("www.lider.cl/catalogo/images/catalogo_no_photo.jpg")
                            .price(180000L)
                            .build();
    }

    public static ProductResponse buildProductEntityMarca1() {
        return ProductResponse.builder()
                            ._id("624b943175d8be9230d537a6")
                            .id(1)
                            .brand("Marca1")
                            .description("Televisión 54''")
                            .image("www.lider.cl/catalogo/images/catalogo_no_photo.jpg")
                            .price(80000L)
                            .build();
    }
}
