package pl.org.grzybek.har.order.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class OrderCreateDto {

    @NotEmpty
    @Valid
    private List<OrderProductCreateDto> products = Collections.emptyList();

    @Valid
    private OrderBuyerCreateDto buyer = new OrderBuyerCreateDto();

}
