package com.gaven.mission5.web.controller;

import com.gaven.mission5.business.service.ProductService;
import com.gaven.mission5.data.assembler.ProductModelAssembler;
import com.gaven.mission5.data.entity.Product;
import com.gaven.mission5.data.repository.ProductRepository;
import com.gaven.mission5.business.exception.EntityNotFoundException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class ProductController {

    private final ProductService productService;

    ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping("/products")
    public CollectionModel<EntityModel<Product>> all(){
        return productService.all();
    }

    @PostMapping("/products")
    public ResponseEntity<?> newProduct(@RequestBody Product newProduct){
        return productService.newProduct(newProduct);
    }

    @GetMapping("/products/{id}")
    public EntityModel<Product> one(@PathVariable Long id){
        return productService.one(id);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<?> replaceProduct(@RequestBody Product newProduct, @PathVariable Long id) throws URISyntaxException{
        return productService.replaceProduct(newProduct, id);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id){
        return productService.deleteProduct(id);
    }
}
