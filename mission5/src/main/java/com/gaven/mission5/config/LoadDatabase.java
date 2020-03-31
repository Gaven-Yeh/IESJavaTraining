package com.gaven.mission5.config;

import com.gaven.mission5.data.Status;
import com.gaven.mission5.data.entity.Product;
import com.gaven.mission5.data.entity.Purchase;
import com.gaven.mission5.data.repository.ProductRepository;
import com.gaven.mission5.data.repository.PurchaseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Configuration
@Slf4j
public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(ProductRepository productRepo, PurchaseRepository purchaseRepo) {
        return args -> {
            log.info("Preloading" + productRepo.save(new Product("Acoustic", "Guitar", "Taylors", 5)));
            log.info("Preloading" + productRepo.save(new Product("Grand", "Piano", "Yamaha", 3)));

            purchaseRepo.save(new Purchase((long) 1, new Date(), Status.COMPLETED));
            purchaseRepo.save(new Purchase((long) 2, new Date(), Status.IN_PROGRESS));

            purchaseRepo.findAll().forEach(purchase -> {
                log.info("Preloaded" + purchase);
            });
        };
    }
}
