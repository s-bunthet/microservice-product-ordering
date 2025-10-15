package io.github.sbunthet.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class OrderLineItemsRequest {
    private String skuCode;
    private BigDecimal price;
    private Integer quantity;
}
