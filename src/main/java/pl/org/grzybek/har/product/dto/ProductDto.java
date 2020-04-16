package pl.org.grzybek.har.product.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ProductDto {

    private UUID sku;
    private String name;
    private BigDecimal price;
    private LocalDateTime creationTime;
}
