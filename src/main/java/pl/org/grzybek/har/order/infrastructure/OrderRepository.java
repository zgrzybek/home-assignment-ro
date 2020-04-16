package pl.org.grzybek.har.order.infrastructure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.org.grzybek.har.common.mongo.SoftDeletableMongoRepository;
import pl.org.grzybek.har.order.domain.model.Order;

import java.time.LocalDateTime;
import java.util.UUID;

public interface OrderRepository extends SoftDeletableMongoRepository<Order, UUID> {
    Page<Order> findByCreationTimeBetweenAndDeletedFalse(Pageable pageable, LocalDateTime from, LocalDateTime to);
}
