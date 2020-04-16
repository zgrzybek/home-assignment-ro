package pl.org.grzybek.har.order.domain;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.org.grzybek.har.order.domain.model.Order;
import pl.org.grzybek.har.order.domain.model.OrderedProduct;
import pl.org.grzybek.har.order.dto.OrderDto;
import pl.org.grzybek.har.order.dto.OrderedProductDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class OrderConverter {

    public OrderDto toDto(Order order) {
        OrderDto dto = new OrderDto();
        dto.setId(order.getId());
        dto.getBuyer().setEmail(order.getBuyer().getEmail());
        dto.setCreationTime(order.getCreationTime());
        dto.setProducts(toOrderProductDtos(order.getOrderedProducts()));
        dto.setTotalPrice(order.getTotalPrice());
        return dto;
    }

    public List<OrderDto> toDtos(List<Order> orders) {
        return orders.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public OrderedProductDto toDto(OrderedProduct orderedProduct) {
        OrderedProductDto dto = new OrderedProductDto();
        dto.setPrice(orderedProduct.getPrice());
        dto.setQuantity(orderedProduct.getQuantity());
        dto.setProductSku(orderedProduct.getProductSku());
        return dto;
    }

    public List<OrderedProductDto> toOrderProductDtos(List<OrderedProduct> orders) {
        return orders.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
