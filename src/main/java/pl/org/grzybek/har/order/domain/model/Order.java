package pl.org.grzybek.har.order.domain.model;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.org.grzybek.har.common.audit.Audited;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "orders")
public class Order extends Audited {

    private List<OrderedProduct> orderedProducts = Lists.newArrayList();
    private BigDecimal totalPrice;
    private OrderBuyer buyer;

}
