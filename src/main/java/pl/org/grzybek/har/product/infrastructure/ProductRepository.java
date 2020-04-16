package pl.org.grzybek.har.product.infrastructure;

import org.springframework.stereotype.Repository;
import pl.org.grzybek.har.common.mongo.SoftDeletableMongoRepository;
import pl.org.grzybek.har.product.domain.model.Product;

import java.util.UUID;

@Repository
public interface ProductRepository extends SoftDeletableMongoRepository<Product, UUID> {
}
