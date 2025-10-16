package io.github.sbunthet.orderservice.service;

import io.github.sbunthet.orderservice.dto.InventoryResponse;
import io.github.sbunthet.orderservice.dto.OrderLineItemsResponse;
import io.github.sbunthet.orderservice.dto.OrderRequest;
import io.github.sbunthet.orderservice.dto.OrderResponse;
import io.github.sbunthet.orderservice.model.Order;
import io.github.sbunthet.orderservice.model.OrderLineItems;
import io.github.sbunthet.orderservice.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient webClient;

    public void placeOrder(OrderRequest orderRequest) {
        Order order = orderRequestToOrder(orderRequest);

        List<String> skuCodes =  order.getOrderLineItemsList().stream().map(OrderLineItems::getSkuCode).toList();

        // call to the inventory service to check if the products are in stock
        InventoryResponse[] inventoryResponseArray = webClient.get()
                .uri("http://localhost:8083/api/inventory", uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        boolean allProductsInStock =  inventoryResponseArray != null &&  List.of(inventoryResponseArray).stream().allMatch(InventoryResponse::isInStock);

        if(allProductsInStock){
            orderRepository.save(order);
        }else{
            throw new IllegalArgumentException("Product is not in stock, please try again later");
        }


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
