package pl.org.grzybek.har.product.domain;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.org.grzybek.har.common.exceptions.ResourceNotFoundException;
import pl.org.grzybek.har.product.dto.ProductCreateDto;
import pl.org.grzybek.har.product.dto.ProductDto;
import pl.org.grzybek.har.product.dto.ProductUpdateDto;
import pl.org.grzybek.har.product.infrastructure.ProductRepository;
import pl.org.grzybek.har.product.domain.model.Product;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final ProductConverter converter;
    private final ProductFactory factory;

    public ProductDto create(ProductCreateDto productCreateDto) {
        Product product = factory.create(productCreateDto);
        Product savedProduct = repository.save(product);
        return converter.toDto(savedProduct);
    }

    public Page<ProductDto> getAll(Pageable pageable) {
        return repository.findAllActive(pageable)
                .map(converter::toDto);
    }

    public ProductDto get(UUID sku) {
        return repository.findActiveById(sku)
                .map(converter::toDto)
                .orElseThrow(ResourceNotFoundException::new);
    }

    public void update(UUID sku, ProductUpdateDto updateDto) {
        Product product = repository.findActiveById(sku)
                .orElseThrow(ResourceNotFoundException::new);
        factory.update(product, updateDto);
        repository.save(product);
    }

    public void softDelete(UUID sku) {
        val product = repository.findActiveById(sku)
                .orElseThrow(ResourceNotFoundException::new);
        repository.softDelete(product);
    }
}
