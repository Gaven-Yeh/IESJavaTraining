package com.gaven.mission7.controller;

import com.gaven.mission7.service.ProductService;
import com.gaven.mission7.entity.Product;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Log4j2
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public CollectionModel<EntityModel<Product>> all(@RequestHeader("userId") long userId) {
        log.debug("User "+ userId + " requested all products");
        return productService.all();
    }

    @PostMapping("/products")
    public ResponseEntity<?> newProduct(@RequestBody Product newProduct, @RequestHeader("userId") long userId) {
        log.debug("User "+ userId + " created new product");
        return productService.newProduct(newProduct);
    }

    @GetMapping("/products/{id}")
    public EntityModel<Product> one(@PathVariable Long id, @RequestHeader("userId") long userId) {
        log.debug("User "+ userId + " requested Product "+ id);
        return productService.one(id);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<?> replaceProduct(@RequestBody Product newProduct, @PathVariable Long id,
                                            @RequestHeader("userId") long userId) throws URISyntaxException {
        log.debug("User "+ userId + " edited Product "+ id);
        return productService.replaceProduct(newProduct, id);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id, @RequestHeader("userId") long userId) {
        log.warn("User "+ userId + " deleted Product "+ id);
        return productService.deleteProduct(id);
    }
}
