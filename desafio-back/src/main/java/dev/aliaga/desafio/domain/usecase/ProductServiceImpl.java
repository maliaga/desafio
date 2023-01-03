package dev.aliaga.desafio.domain.usecase;

import dev.aliaga.desafio.domain.port.primary.ProductService;
import dev.aliaga.desafio.infrastructure.adapters.repositories.ProductRepository;
import dev.aliaga.desafio.presentation.api.model.ProductResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<ProductResponse> getAll() {
        return productRepository.findAll().stream().map(ProductResponse::from).collect(java.util.stream.Collectors.toList());
    }

    @Override
    public ProductResponse getById(String id) {
        return ProductResponse.from(productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found")));
    }
}
