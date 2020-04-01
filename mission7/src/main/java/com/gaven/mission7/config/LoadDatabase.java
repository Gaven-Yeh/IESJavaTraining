package com.gaven.mission7.config;

import com.gaven.mission7.Status;
import com.gaven.mission7.entity.Product;
import com.gaven.mission7.entity.Purchase;
import com.gaven.mission7.repository.ProductRepository;
import com.gaven.mission7.repository.PurchaseRepository;
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
