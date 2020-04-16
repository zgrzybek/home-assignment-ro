package pl.org.grzybek.har.order.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class OrderDto {

    private UUID id;
    private List<OrderedProductDto> products = Collections.emptyList();
    private BigDecimal totalPrice;
    private LocalDateTime creationTime;
    private OrderBuyerDto buyer = new OrderBuyerDto();
}
