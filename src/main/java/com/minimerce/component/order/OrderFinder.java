package com.minimerce.component.order;

import com.minimerce.domain.order.Order;
import com.minimerce.domain.order.OrderRepository;
import com.minimerce.object.order.FindOrderRequest;
import com.minimerce.support.exception.NotExistOrderException;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by gemini on 20/04/2017.
 */
@Component
public class OrderFinder {
    private final OrderRepository orderRepository;

    @Inject
    public OrderFinder(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order findOrder(Long clientId, FindOrderRequest request) throws NotExistOrderException {
        if(request.isFindByOrderId()) return orderRepository.findByClientIdAndId(clientId, request.getOrderId());
        if(request.isFindByClientOrderId()) return orderRepository.findByClientIdAndClientOrderId(clientId, request.getClientOrderId());
        throw new NotExistOrderException("Not Found Order");
    }

    public List<Order> findOrders(Long clientId, Long customerId) {
        return orderRepository.findByClientIdAndCustomerId(clientId, customerId);
    }
}