package io.github.sbunthet.inventoryservice;

import io.github.sbunthet.inventoryservice.model.Inventory;
import io.github.sbunthet.inventoryservice.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }

    @Bean
    /**
     * In Spring Boot, any CommandLineRunner bean is automatically executed once,
     * right after the application context has been initialized — i.e. just before the app starts accepting requests.
     * - The database connection is ready.
     * - The InventoryRepository is injected.
     * - Then the code inside the lambda (args -> { … }) runs once at startup.
     * **/
    public CommandLineRunner loadData(InventoryRepository inventoryRepository) {
        return args -> {
            inventoryRepository.deleteAll();
            inventoryRepository.save(new Inventory(null, "iphone-13", 100));
            inventoryRepository.save(new Inventory(null, "samsung-galaxy-s21", 50));
            inventoryRepository.save(new Inventory(null, "oneplus-9", 75));
        };
    }

}
