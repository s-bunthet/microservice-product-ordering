package io.github.sbunthet.inventoryservice.service;

import io.github.sbunthet.inventoryservice.dto.InventoryResponse;
import io.github.sbunthet.inventoryservice.model.Inventory;
import io.github.sbunthet.inventoryservice.repository.InventoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    public List<InventoryResponse> isInStock(List<String> skuCodes) {
        return inventoryRepository.findBySkuCodeIn(skuCodes).stream().map(this::mapInventoryToInventoryResponse).toList();
    }

    private InventoryResponse mapInventoryToInventoryResponse(Inventory inventory) {
        return InventoryResponse.builder()
                .skuCode(inventory.getSkuCode())
                .isInStock(inventory.getQuantity() > 0)
                .build();
    }
}
