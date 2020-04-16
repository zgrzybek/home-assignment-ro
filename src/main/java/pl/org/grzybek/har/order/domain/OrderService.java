package pl.org.grzybek.har.order.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.org.grzybek.har.common.exceptions.ResourceNotFoundException;
import pl.org.grzybek.har.order.domain.model.Order;
import pl.org.grzybek.har.order.dto.OrderDto;
import pl.org.grzybek.har.order.dto.OrderCreateDto;
import pl.org.grzybek.har.order.infrastructure.OrderRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderFactory orderFactory;
    private final OrderRepository orderRepository;
    private final OrderConverter orderConverter;

    public OrderDto place(OrderCreateDto createDto) {
        Order order = orderFactory.createOrder(createDto);
        Order saved = orderRepository.save(order);
        return orderConverter.toDto(saved);
    }

    public OrderDto get(UUID orderId) {
        return orderRepository.findActiveById(orderId)
                .map(orderConverter::toDto)
                .orElseThrow(ResourceNotFoundException::new);
    }

    public Page<OrderDto> findAll(Pageable pageable, OrderSearchCriteria query) {
        return orderRepository
                .findByCreationTimeBetweenAndDeletedFalse(pageable, query.getFromTime(), query.getToTime())
                .map(orderConverter::toDto);
    }
}
