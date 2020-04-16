package pl.org.grzybek.har.order.domain;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class OrderSearchCriteria {
    private final LocalDateTime fromTime;
    private final LocalDateTime toTime;
}
