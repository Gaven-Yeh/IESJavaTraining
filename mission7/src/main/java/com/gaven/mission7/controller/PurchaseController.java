package com.gaven.mission7.controller;

import com.gaven.mission7.service.PurchaseService;
import com.gaven.mission7.entity.Purchase;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Log4j2
@RestController
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @GetMapping("/purchases")
    public CollectionModel<EntityModel<Purchase>> viewPurchases(@RequestHeader("userId") long userId) {
        log.debug("User "+ userId + " requested all purchases");
        return purchaseService.viewPurchases();
    }

    @GetMapping("/purchases/{id}")
    public EntityModel<Purchase> getOne(@PathVariable Long id, @RequestHeader("userId") long userId) {
        log.debug("User "+ userId + " requested Purchase "+id);
        return purchaseService.getOne(id);
    }

    @PostMapping("/purchases")
    public ResponseEntity<?> addToCart(@RequestBody Purchase newPurchase, @RequestHeader("userId") long userId) {
        log.debug("User "+ userId + " added Product " + newPurchase.getProduct_id() + " to cart");
        return purchaseService.addToCart(newPurchase);
    }

    @PutMapping("/purchases/{id}")
    public ResponseEntity<?> editPurchase(@RequestBody Purchase newPurchase, @PathVariable Long id,
                                          @RequestHeader("userId") long userId) throws URISyntaxException {
        log.debug("User "+ userId + " edited Purchase "+id);
        return purchaseService.editPurchase(newPurchase, id);
    }

    @PutMapping("/purchases/{id}/complete")
    public ResponseEntity<?> completePurchase(@PathVariable Long id, @RequestHeader("userId") long userId) {
        log.debug("User "+ userId + " completed Purchase "+id);
        return purchaseService.completePurchase(id);
    }

    @DeleteMapping("/purchases/{id}/cancel")
    public ResponseEntity<RepresentationModel> cancelPurchase(@PathVariable Long id, @RequestHeader("userId") long userId) {
        log.debug("User "+ userId + " cancelled Purchase "+id );
        return purchaseService.cancelPurchase(id);
    }
}
