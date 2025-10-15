package io.github.sbunthet.orderservice.service;

import io.github.sbunthet.orderservice.dto.OrderLineItemsResponse;
import io.github.sbunthet.orderservice.dto.OrderRequest;
import io.github.sbunthet.orderservice.dto.OrderResponse;
import io.github.sbunthet.orderservice.model.Order;
import io.github.sbunthet.orderservice.model.OrderLineItems;
import io.github.sbunthet.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public void placeOrder(OrderRequest orderRequest) {
        Order order = orderRequestToOrder(orderRequest);
        orderRepository.save(order);

    }

    public List<OrderResponse> getOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(this::orderToOrderResponse).toList();
    }

    private Order orderRequestToOrder(OrderRequest orderRequest) {
        return Order.builder()
                .orderNumber(orderRequest.getOrderNumber())
                .orderLineItemsList(orderRequest.getOrderLineItemsRequestList().stream().map(this::orderLineItemsRequestToOrderLineItems).toList())
                .build();
    }

    private OrderLineItems orderLineItemsRequestToOrderLineItems(io.github.sbunthet.orderservice.dto.OrderLineItemsRequest orderLineItemsRequest) {
        return OrderLineItems.builder()
                .skuCode(orderLineItemsRequest.getSkuCode())
                .price(orderLineItemsRequest.getPrice())
                .quantity(orderLineItemsRequest.getQuantity())
                .build();
    }
    public OrderResponse orderToOrderResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .orderLineItemsResponseList(order.getOrderLineItemsList().stream().map(this::orderLineItemsToOrderLineItemsResponse).toList())
                .build();
    }

    private OrderLineItemsResponse orderLineItemsToOrderLineItemsResponse(OrderLineItems orderLineItems) {
        return io.github.sbunthet.orderservice.dto.OrderLineItemsResponse.builder()
                .id(orderLineItems.getId())
                .skuCode(orderLineItems.getSkuCode())
                .price(orderLineItems.getPrice())
                .quantity(orderLineItems.getQuantity())
                .build();
    }
}
