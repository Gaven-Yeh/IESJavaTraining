package com.gaven.mission5.business.service;

import com.gaven.mission5.business.exception.EntityNotFoundException;
import com.gaven.mission5.data.assembler.ProductModelAssembler;
import com.gaven.mission5.data.entity.Product;
import com.gaven.mission5.data.repository.ProductRepository;
import com.gaven.mission5.web.controller.ProductController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepo;
    @Autowired
    private ProductModelAssembler productAss;

    public CollectionModel<EntityModel<Product>> all() {
        List<EntityModel<Product>> products = productRepo.findAll().stream()
                .map(productAss::toModel)
                .collect(Collectors.toList());

        return new CollectionModel<>(products,
                linkTo(methodOn(ProductController.class).all()).withSelfRel());
    }

    public ResponseEntity<?> newProduct(@RequestBody Product newProduct) {
        EntityModel<Product> entityModel = productAss.toModel(productRepo.save(newProduct));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    public EntityModel<Product> one(@PathVariable Long id) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, Product.class.getSimpleName()));
        return productAss.toModel(product);
    }

    public ResponseEntity<?> replaceProduct(@RequestBody Product newProduct, @PathVariable Long id) throws URISyntaxException {

        Product editedProduct = productRepo.findById(id)
                .map(product -> {
                    product.setName(newProduct.getName());
                    product.setBrand(newProduct.getBrand());
                    product.setStock(newProduct.getStock());
                    return productRepo.save(product);
                })
                .orElseGet(() -> {
                    newProduct.setProduct_id(id);
                    return productRepo.save(newProduct);
                });

        EntityModel<Product> entityModel = productAss.toModel(editedProduct);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        productRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
