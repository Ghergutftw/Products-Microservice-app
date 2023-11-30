package app.controller;

import app.dto.InventoryResponse;
import app.model.Inventory;
import app.repository.InventoryRepository;
import app.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
@Slf4j
public class InventoryController {

    private final InventoryService inventoryService;

    private final InventoryRepository  inventoryRepository;

//    http://localhost:8082/api/inventory/iphone-13,iphone-13-red
//    http://localhost:8082/api/inventory?sku-code=iphone-13&sku-code=iphone-13-red
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode) throws InterruptedException {
        log.info("Received inventory check request for skuCode: {}", skuCode);
        return inventoryService.isInStock(skuCode);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Inventory> isInStock() throws InterruptedException {
        return inventoryRepository.findAll();
    }
}
