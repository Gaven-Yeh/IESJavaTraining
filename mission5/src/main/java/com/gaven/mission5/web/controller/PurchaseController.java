package com.gaven.mission5.web.controller;

import com.gaven.mission5.business.service.PurchaseService;
import com.gaven.mission5.data.Status;
import com.gaven.mission5.data.assembler.PurchaseModelAssembler;
import com.gaven.mission5.data.entity.Purchase;
import com.gaven.mission5.data.repository.PurchaseRepository;
import com.gaven.mission5.business.exception.EntityNotFoundException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.mediatype.vnderrors.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class PurchaseController {

    private final PurchaseService purchaseService;

    PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @GetMapping("/purchases")
    public CollectionModel<EntityModel<Purchase>> viewPurchases(){
        return purchaseService.viewPurchases();
    }

    @GetMapping("/purchases/{id}")
    public EntityModel<Purchase> getOne(@PathVariable Long id){
        return purchaseService.getOne(id);
    }

    @PostMapping("/purchases")
    public ResponseEntity<?> addToCart(@RequestBody Purchase newPurchase) {
        return purchaseService.addToCart(newPurchase);
    }

    @PutMapping("/purchases/{id}")
    public ResponseEntity<?> editPurchase(@RequestBody Purchase newPurchase, @PathVariable Long id) throws URISyntaxException{
        return purchaseService.editPurchase(newPurchase, id);
    }

    @PutMapping("/purchases/{id}/complete")
    public ResponseEntity<?> completePurchase(@PathVariable Long id){
        return purchaseService.completePurchase(id);
    }

    @DeleteMapping("/purchases/{id}/cancel")
    public ResponseEntity<RepresentationModel> cancelPurchase(@PathVariable Long id){
        return purchaseService.cancelPurchase(id);
    }
}
