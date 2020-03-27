package com.gaven.mission5.web.controller;

import com.gaven.mission5.data.entity.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.EntityModel;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ProductControllerIntegrationTests {

    @Autowired
    private ProductController productController;

    @Test
    public void givenProductControllerWhenQueriedWithAnIdThenExpectedProduct() {
        Date date = new Date();
        Product product = new Product((long) 5,"Classical", "Guitar", "Cordoba", 3);
        productController.newProduct(product);

        EntityModel<Product> result = productController.one((long) 5);

        System.out.println(product.getName() + result.getContent().getName());
        Assertions.assertEquals(product.getName(), result.getContent().getName());

    }
}
