package com.gaven.mission7.assembler;

import com.gaven.mission7.entity.Product;
import com.gaven.mission7.controller.ProductController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProductModelAssembler implements RepresentationModelAssembler<Product, EntityModel<Product>> {

    @Override
    public EntityModel<Product> toModel(Product product) {
        return new EntityModel<>(product,
                linkTo(methodOn(ProductController.class).one(product.getProduct_id(),0)).withSelfRel(),
                linkTo(methodOn(ProductController.class).all(0)).withRel("products"));
    }
}
