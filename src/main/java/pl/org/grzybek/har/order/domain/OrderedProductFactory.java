package pl.org.grzybek.har.order.domain;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.org.grzybek.har.common.exceptions.ResourceNotFoundException;
import pl.org.grzybek.har.order.domain.model.OrderedProduct;
import pl.org.grzybek.har.order.dto.OrderCreateDto;
import pl.org.grzybek.har.order.dto.OrderProductCreateDto;
import pl.org.grzybek.har.product.domain.model.Product;
import pl.org.grzybek.har.product.infrastructure.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
class OrderedProductFactory {

    private final ProductRepository productRepository;

    public List<OrderedProduct> createOrderedProducts(OrderCreateDto createDto) {
        return createDto.getProducts()
                .stream()
                .map(this::createOrderedProduct)
                .collect(Collectors.toList());
    }

    private OrderedProduct createOrderedProduct(OrderProductCreateDto dto) {
        Product product = productRepository.findActiveById(dto.getProductSku())
                .orElseThrow(ResourceNotFoundException::new);

        OrderedProduct orderedProduct = new OrderedProduct();
        orderedProduct.setPrice(product.getPrice());
        orderedProduct.setProductSku(dto.getProductSku());
        orderedProduct.setQuantity(dto.getQuantity());
        return orderedProduct;
    }
}
