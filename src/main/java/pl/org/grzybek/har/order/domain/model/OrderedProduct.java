package pl.org.grzybek.har.order.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.UUID;

@Getter
@Setter
public class OrderedProduct {

    private UUID productSku;
    private BigInteger quantity;
    private BigDecimal price;

}
