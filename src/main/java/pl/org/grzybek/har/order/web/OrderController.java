package pl.org.grzybek.har.order.web;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.org.grzybek.har.order.domain.OrderSearchCriteria;
import pl.org.grzybek.har.order.domain.OrderService;
import pl.org.grzybek.har.order.dto.OrderDto;
import pl.org.grzybek.har.order.dto.OrderCreateDto;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
@RequiredArgsConstructor
@RequestMapping(OrderController.ORDERS_V1_URL)
public class OrderController {

    public static final String ORDERS_V1_URL = "/api/v1/orders";

    private final OrderService orderService;

    @PostMapping
    public OrderDto create(@Valid @RequestBody OrderCreateDto dto) {
        return orderService.place(dto);
    }

    @GetMapping("/{id}")
    public OrderDto get(@PathVariable UUID id) {
        return orderService.get(id);
    }

    @GetMapping
    public Page<OrderDto> findAll(
            @PageableDefault(direction = DESC, sort = "creationTime") Pageable pageable,
            @RequestParam("from") LocalDateTime fromTime,
            @RequestParam("to") LocalDateTime toTime) {
        val query = OrderSearchCriteria.builder()
                .fromTime(fromTime)
                .toTime(toTime)
                .build();
        return orderService.findAll(pageable, query);
    }
}
