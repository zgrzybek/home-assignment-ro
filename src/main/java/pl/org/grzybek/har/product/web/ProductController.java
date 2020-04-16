package pl.org.grzybek.har.product.web;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.org.grzybek.har.product.domain.ProductService;
import pl.org.grzybek.har.product.dto.ProductCreateDto;
import pl.org.grzybek.har.product.dto.ProductDto;
import pl.org.grzybek.har.product.dto.ProductUpdateDto;

import javax.validation.Valid;

import java.util.UUID;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
@RequestMapping(ProductController.PRODUCTS_V1_URL)
@AllArgsConstructor
public class ProductController {

    public static final String PRODUCTS_V1_URL = "/api/v1/products";

    private final ProductService service;

    @PostMapping
    public ProductDto create(@Valid @RequestBody ProductCreateDto dto) {
        return service.create(dto);
    }

    @GetMapping
    public Page<ProductDto> findAll(@PageableDefault(direction = DESC, sort = "creationTime") Pageable pageable) {
        return service.getAll(pageable);
    }

    @GetMapping("/{sku}")
    public ProductDto get(@PathVariable(value = "sku") UUID sku) {
        return service.get(sku);
    }

    @PutMapping("/{sku}")
    public void update(@Valid @RequestBody ProductUpdateDto dto,
            @PathVariable(value = "sku") UUID sku) {
        service.update(sku, dto);
    }

    @DeleteMapping("/{sku}")
    public void softDelete(@PathVariable(value = "sku") UUID sku) {
        service.softDelete(sku);
    }

}
