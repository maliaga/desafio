package dev.aliaga.desafio.presentation.api.controller;

import dev.aliaga.desafio.domain.port.primary.ProductService;
import dev.aliaga.desafio.presentation.api.model.ProductResponse;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@Api(tags = "Product Methods")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", methods= {RequestMethod.GET})
public class ProductController {

    public final ProductService productService;

    @GetMapping( "/products")
    public List<ProductResponse> getAllProducts() {
        return productService.getAll();
    }
}
