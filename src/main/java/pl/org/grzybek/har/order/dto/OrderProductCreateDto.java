package pl.org.grzybek.har.order.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigInteger;
import java.util.UUID;

@Getter
@Setter
public class OrderProductCreateDto {

    @NotNull
    private UUID productSku;

    @NotNull
    @Positive
    private BigInteger quantity;
}
