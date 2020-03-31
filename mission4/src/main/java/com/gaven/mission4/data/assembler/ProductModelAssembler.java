package com.gaven.mission4.data.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.gaven.mission4.data.entity.Product;
import com.gaven.mission4.web.controller.ProductController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class ProductModelAssembler implements RepresentationModelAssembler<Product, EntityModel<Product>> {

    @Override
    public EntityModel<Product> toModel(Product purchase) {
        return new EntityModel<>(purchase,
                linkTo(methodOn(ProductController.class).one(purchase.getProduct_id())).withSelfRel(),
                linkTo(methodOn(ProductController.class).all()).withRel("products"));
    }
}
