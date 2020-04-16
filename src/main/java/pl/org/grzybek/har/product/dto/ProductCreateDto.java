package pl.org.grzybek.har.product.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Getter
@Setter
public class ProductCreateDto {

    @NotEmpty
    private String name;

    @NotNull
    @Positive
    private BigDecimal price;
}
