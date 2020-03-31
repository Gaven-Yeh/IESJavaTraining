package com.gaven.mission5;

import com.gaven.mission5.data.Status;
import com.gaven.mission5.data.entity.Product;
import com.gaven.mission5.web.controller.ProductController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductApplicationE2eTests {

    @Autowired
    private ProductController productController;

    @LocalServerPort
    private int port;

    @Test
    public void givenProductApplicationWhenQueriedWithAnIdThenGetExpectedMovie() {
        Product product = new Product((long) 5, "Classical", "Guitar", "Cordoba", 3);
        productController.newProduct(product);

        when().get(String.format("http://localhost:%s/products/5", port))
                .then().statusCode(is(200))
                .body("stock", equalTo(3));
    }

}
