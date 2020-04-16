package pl.org.grzybek.har.product.domain;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.org.grzybek.har.product.domain.model.Product;
import pl.org.grzybek.har.product.dto.ProductCreateDto;
import pl.org.grzybek.har.product.dto.ProductUpdateDto;

@Service
@AllArgsConstructor
public class ProductFactory {

    private final ProductSkuProvider productSkuProvider;

    public Product create(ProductCreateDto productCreateDto) {
        Product product = new Product();
        product.setName(productCreateDto.getName());
        product.setPrice(productCreateDto.getPrice());
        product.setId(productSkuProvider.provideNew());
        return product;
    }

    public void update(Product product, ProductUpdateDto updateDto) {
        product.setPrice(updateDto.getPrice());
        product.setName(updateDto.getName());
    }
}
