package com.gaven.mission5;

import com.gaven.mission5.business.exception.EntityNotFoundException;
import com.gaven.mission5.data.Status;
import com.gaven.mission5.data.entity.Product;
import com.gaven.mission5.data.entity.Purchase;
import com.gaven.mission5.data.repository.ProductRepository;
import com.gaven.mission5.web.controller.PurchaseController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PurchaseApplicationE2eTests {

    @Autowired
    private PurchaseController purchaseController;
    @Autowired
    private ProductRepository productRepository;

    @LocalServerPort
    private int port;

    @Test
    public void givenPurchaseApplicationWhenQueriedWithAnIdThenGetExpectedMovie() {
        Date date = new Date();
        Purchase purchase = new Purchase(5L, date, Status.IN_PROGRESS);
        purchaseController.addToCart(purchase);

        when().get(String.format("http://localhost:%s/purchases/5", port))
                .then().statusCode(is(200))
                .body("status", is("IN_PROGRESS"));
    }

    @Test
    public void completePurchaseTest() {
        Date date = new Date();
        Purchase purchase = new Purchase(5L, 6L, date, Status.IN_PROGRESS);
        Product product = new Product(6L, "Classical", "Guitar", "Cordoba", 2);
        purchaseController.addToCart(purchase);
        productRepository.save(product);

        when().put(String.format("http://localhost:%s/purchases/5/complete", port))
                .then().statusCode(is(200))
                .body("status", is("COMPLETED"));

        Product updatedProduct = productRepository.findById(product.getProduct_id()).
                orElseThrow(() -> new EntityNotFoundException(product.getProduct_id(), Product.class.getSimpleName()));

        assertThat(updatedProduct.getStock(), equalTo(1));
    }
}
