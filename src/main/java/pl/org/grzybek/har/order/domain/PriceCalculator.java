package pl.org.grzybek.har.order.domain;

import org.springframework.stereotype.Service;
import pl.org.grzybek.har.order.domain.model.OrderedProduct;

import java.math.BigDecimal;
import java.util.List;

import static java.math.BigDecimal.ZERO;

@Service
public class PriceCalculator {

    public BigDecimal calculateTotal(List<OrderedProduct> orderedProducts) {
        return orderedProducts.stream()
                .map(it -> new BigDecimal(it.getQuantity()).multiply(it.getPrice()))
                .reduce(ZERO, BigDecimal::add);
    }
}
