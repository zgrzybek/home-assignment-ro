package pl.org.grzybek.har.product.domain;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.org.grzybek.har.product.domain.model.Product;
import pl.org.grzybek.har.product.dto.ProductDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductConverter {

    public ProductDto toDto(Product product) {
        ProductDto dto = new ProductDto();
        dto.setSku(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setCreationTime(product.getCreationTime());
        return dto;
    }

    public List<ProductDto> toDtos(List<Product> products) {
        return products.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

}
