package io.github.sbunthet.orderservice.dto;

import io.github.sbunthet.orderservice.model.OrderLineItems;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class OrderRequest {
    private String orderNumber;
    private List<OrderLineItemsRequest> orderLineItemsRequestList;
}
