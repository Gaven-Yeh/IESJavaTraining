package com.gaven.mission5.web.controller;

import com.gaven.mission5.business.exception.EntityNotFoundException;
import com.gaven.mission5.data.Status;
import com.gaven.mission5.data.entity.Product;
import com.gaven.mission5.data.entity.Purchase;
import com.gaven.mission5.data.repository.ProductRepository;
import com.gaven.mission5.data.repository.PurchaseRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PurchaseControllerIntegrationTests {

    @Autowired
    private PurchaseController purchaseController;
    @Autowired
    private ProductRepository prodRepo;
    @Autowired
    private PurchaseRepository purchRepo;

    @Test
    public void givenPurchaseControllerWhenQueriedWithAnIdThenExpectedPurchase() {
        Date date = new Date();
        Purchase purchase = new Purchase(5L, 3L, date, Status.IN_PROGRESS);
        purchaseController.addToCart(purchase);

        EntityModel<Purchase> result = purchaseController.getOne(5L);

//        System.out.println(purchase.getDate());
//        System.out.print(result.getContent().getDate());
        Assertions.assertEquals(purchase.getDate(), result.getContent().getDate());

    }

    @Test
    public void completedPurchaseTest() {
        Date date = new Date();
        Purchase purchase = new Purchase(5L, 6L, date, Status.IN_PROGRESS);
        Product product = new Product(6L, "Classical", "Guitar", "Cordoba", 2);
        purchRepo.save(purchase);
        prodRepo.save(product);

        ResponseEntity<?> result = purchaseController.completePurchase(purchase.getPurchase_id());

        Product updatedProduct = prodRepo.findById(product.getProduct_id()).
                orElseThrow(()-> new EntityNotFoundException(product.getProduct_id(), Product.class.getSimpleName()));

        assertThat(result.getBody().toString(), containsString("COMPLETED"));
        assertThat(updatedProduct.getStock(), equalTo(1));

    }
}
