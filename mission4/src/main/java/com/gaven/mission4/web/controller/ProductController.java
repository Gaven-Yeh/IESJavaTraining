package com.gaven.mission4.web.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.gaven.mission4.data.assembler.ProductModelAssembler;
import com.gaven.mission4.exception.EntityNotFoundException;
import com.gaven.mission4.data.entity.Product;
import com.gaven.mission4.data.repository.ProductRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProductController {

    private final ProductRepository repository;
    private final ProductModelAssembler assembler;

    ProductController(ProductRepository repository, ProductModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/products")
    public CollectionModel<EntityModel<Product>> all() {
        List<EntityModel<Product>> products = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return new CollectionModel<>(products,
                linkTo(methodOn(ProductController.class).all()).withSelfRel());
    }

    @PostMapping("/products")
    public ResponseEntity<?> newProduct(@RequestBody Product newProduct) throws URISyntaxException {
        EntityModel<Product> entityModel = assembler.toModel(repository.save(newProduct));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @GetMapping("/products/{id}")
    public EntityModel<Product> one(@PathVariable Long id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, Product.class.getSimpleName()));
        return assembler.toModel(product);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<?> replaceProduct(@RequestBody Product newProduct, @PathVariable Long id) throws URISyntaxException {

        Product editedProduct = repository.findById(id)
                .map(product -> {
                    product.setName(newProduct.getName());
                    product.setBrand(newProduct.getBrand());
                    product.setStock(newProduct.getStock());
                    return repository.save(product);
                })
                .orElseGet(() -> {
                    newProduct.setProduct_id(id);
                    return repository.save(newProduct);
                });

        EntityModel<Product> entityModel = assembler.toModel(editedProduct);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
