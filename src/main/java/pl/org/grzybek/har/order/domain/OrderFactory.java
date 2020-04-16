package pl.org.grzybek.har.order.domain;

import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;
import pl.org.grzybek.har.order.domain.model.Order;
import pl.org.grzybek.har.order.domain.model.OrderBuyer;
import pl.org.grzybek.har.order.dto.OrderCreateDto;

import java.util.UUID;

@Component
@AllArgsConstructor
class OrderFactory {

    private final PriceCalculator priceCalculator;
    private final OrderedProductFactory orderedProductFactory;

    Order createOrder(OrderCreateDto createDto) {
        Order order = new Order();

        order.setId(UUID.randomUUID());
        order.setOrderedProducts(orderedProductFactory.createOrderedProducts(createDto));
        order.setBuyer(createBuyer(createDto));
        order.setTotalPrice(priceCalculator.calculateTotal(order.getOrderedProducts()));
        return order;
    }

    private OrderBuyer createBuyer(OrderCreateDto createDto) {
        val buyer = new OrderBuyer();
        buyer.setEmail(createDto.getBuyer().getEmail());
        return buyer;
    }
}
