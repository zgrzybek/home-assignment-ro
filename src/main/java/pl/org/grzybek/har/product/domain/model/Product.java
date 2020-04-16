package pl.org.grzybek.har.product.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.org.grzybek.har.common.audit.Audited;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "products")
public class Product extends Audited {
    private String name;
    private BigDecimal price;

}
