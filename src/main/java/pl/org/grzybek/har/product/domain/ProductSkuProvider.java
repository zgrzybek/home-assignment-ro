package pl.org.grzybek.har.product.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class ProductSkuProvider {

    public UUID provideNew() {
        return UUID.randomUUID();
    }
}
