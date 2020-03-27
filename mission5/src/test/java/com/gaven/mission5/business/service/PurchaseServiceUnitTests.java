package com.gaven.mission5.business.service;

import com.gaven.mission5.data.Status;
import com.gaven.mission5.data.assembler.ProductModelAssembler;
import com.gaven.mission5.data.assembler.PurchaseModelAssembler;
import com.gaven.mission5.data.entity.Product;
import com.gaven.mission5.data.entity.Purchase;
import com.gaven.mission5.data.repository.ProductRepository;
import com.gaven.mission5.data.repository.PurchaseRepository;
import com.gaven.mission5.web.controller.ProductController;
import com.gaven.mission5.web.controller.PurchaseController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


public class PurchaseServiceUnitTests {

    @InjectMocks
    private PurchaseService purchaseService;

    @Mock
    private PurchaseRepository purchaseRepository;
    @Mock
    private PurchaseModelAssembler purchaseModelAssembler;
    @Mock
    private ProductModelAssembler productModelAssembler;
    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getOnePurchaseTest(){
        Date date = new Date();
        Purchase purchase = new Purchase(5L, date, Status.IN_PROGRESS);
        EntityModel<Purchase> entityModel = new EntityModel<>(purchase,
                linkTo(methodOn(ProductController.class).one(purchase.getProduct_id())).withSelfRel(),
                linkTo(methodOn(ProductController.class).all()).withRel("purchases"));
        Mockito.when(purchaseRepository.findById((long) 5)).thenReturn(Optional.ofNullable(purchase));
        Mockito.when(purchaseModelAssembler.toModel(purchase)).thenReturn(entityModel);

        EntityModel<Purchase> result = purchaseService.getOne(5L);

        Assertions.assertEquals(purchase.getDate(), result.getContent().getDate());
    }

    @Test
    public void completePurchaseTest() {
        Date date = new Date();
        Purchase purchase = new Purchase(5L, date, Status.IN_PROGRESS);
        Purchase updatedPurchase = new Purchase(5L, date, Status.COMPLETED);
        EntityModel<Purchase> purchEntityModel = new EntityModel<>(purchase,
                linkTo(methodOn(PurchaseController.class).getOne(purchase.getProduct_id())).withSelfRel(),
                linkTo(methodOn(PurchaseController.class).viewPurchases()).withRel("purchases"));
        EntityModel<Purchase> updatedPurchEntityModel = new EntityModel<>(updatedPurchase,
                linkTo(methodOn(PurchaseController.class).getOne(purchase.getProduct_id())).withSelfRel(),
                linkTo(methodOn(PurchaseController.class).viewPurchases()).withRel("purchases"));
        ResponseEntity<?> purchResponse = ResponseEntity.ok(purchEntityModel);
        ResponseEntity<?> updatedPurchResponse = ResponseEntity.ok(purchEntityModel);


        Product product = new Product(5L, "Classical", "Guitar", "Cordoba", 2);
        Product updatedProduct = new Product(5L, "Classical", "Guitar", "Cordoba", 1);
        EntityModel<Product> prodEntityModel = new EntityModel<>(product,
                linkTo(methodOn(ProductController.class).one(purchase.getProduct_id())).withSelfRel(),
                linkTo(methodOn(ProductController.class).all()).withRel("products"));
        EntityModel<Product> updatedProdEntityModel = new EntityModel<>(updatedProduct,
                linkTo(methodOn(ProductController.class).one(purchase.getProduct_id())).withSelfRel(),
                linkTo(methodOn(ProductController.class).all()).withRel("products"));
        ResponseEntity<?> prodResponse = ResponseEntity.ok(prodEntityModel);
        ResponseEntity<?> updatedProdResponse = ResponseEntity.ok(updatedProdEntityModel);



        Mockito.when(purchaseRepository.findById(5L)).thenReturn(Optional.of(purchase));
        Mockito.when(purchaseRepository.save(purchase)).thenReturn(updatedPurchase);
        Mockito.when(purchaseModelAssembler.toModel(purchase)).thenReturn(purchEntityModel);
        Mockito.when(productRepository.findById(5L)).thenReturn(Optional.ofNullable(product));
        Mockito.when(productRepository.save(updatedProduct)).thenReturn(updatedProduct);
        Mockito.when(productModelAssembler.toModel(product)).thenReturn(prodEntityModel);
        Mockito.when(productModelAssembler.toModel(updatedProduct)).thenReturn(updatedProdEntityModel);

        ResponseEntity<?> result = purchaseService.completePurchase(5L);

        Assertions.assertEquals(purchResponse, result);




    }

}
