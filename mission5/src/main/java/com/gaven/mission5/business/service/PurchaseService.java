package com.gaven.mission5.business.service;

import com.gaven.mission5.business.exception.EntityNotFoundException;
import com.gaven.mission5.data.Status;
import com.gaven.mission5.data.assembler.PurchaseModelAssembler;
import com.gaven.mission5.data.entity.Product;
import com.gaven.mission5.data.entity.Purchase;
import com.gaven.mission5.data.repository.ProductRepository;
import com.gaven.mission5.data.repository.PurchaseRepository;
import com.gaven.mission5.web.controller.PurchaseController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.mediatype.vnderrors.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class PurchaseService {

    private final PurchaseRepository purchRepo;
    private final ProductRepository prodRepo;
    private final PurchaseModelAssembler purchAss;
    
    PurchaseService(PurchaseRepository purchRepo, PurchaseModelAssembler purchAss, ProductRepository prodRepo){
        this.purchRepo = purchRepo;
        this.purchAss = purchAss;
        this.prodRepo = prodRepo;
    }

    public CollectionModel<EntityModel<Purchase>> viewPurchases(){

        List<EntityModel<Purchase>> purchases = purchRepo.findAll().stream()
                .map(purchAss::toModel)
                .collect(Collectors.toList());
        return new CollectionModel<>(purchases,
                linkTo(methodOn(PurchaseController.class).viewPurchases()).withSelfRel());
    }

    public EntityModel<Purchase> getOne(@PathVariable Long id){
        Purchase purchase = purchRepo.findById(id)
                .orElseThrow(()-> new EntityNotFoundException(id, Purchase.class
                        .getSimpleName()));
        return purchAss.toModel(purchase);
    }

    public ResponseEntity<?> addToCart(@RequestBody Purchase newPurchase) {

        if(newPurchase.getDate()==null){
            newPurchase.setDate(new Date());
        }

        newPurchase.setStatus(Status.IN_PROGRESS);

        EntityModel<Purchase> entityModel=purchAss.toModel(purchRepo.save(newPurchase));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    public ResponseEntity<?> editPurchase(@RequestBody Purchase newPurchase, @PathVariable Long id) throws URISyntaxException{

        Purchase editedPurchase = purchRepo.findById(id)
                .map(purchase -> {
                    purchase.setProduct_id(newPurchase.getProduct_id());
                    return purchRepo.save(purchase);
                })
                .orElseGet(()->{
                    newPurchase.setPurchase_id(id);
                    return purchRepo.save(newPurchase);
                });

        EntityModel<Purchase> entityModel = purchAss.toModel(editedPurchase);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    public ResponseEntity<?> completePurchase(@PathVariable Long id) {

        Purchase purchase = purchRepo.findById(id).orElseThrow(() -> new EntityNotFoundException(id, Purchase.class.getSimpleName()));

        // if invalid state (i.e. COMPLETED OR CANCELLED)
        if (purchase.getStatus() != Status.IN_PROGRESS) {
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                    .body(new VndErrors.VndError("Method not allowed",
                            "You can't complete a purchase that is in the " + purchase.getStatus() + " status"));
        }
        purchase.setStatus(Status.COMPLETED);

        // reduce stock or relevant product by one
        Product purchasedProduct = prodRepo.findById(purchase.getProduct_id())
                .map(product -> {
                    product.setStock(product.getStock()-1);
                    return prodRepo.save(product);
                })
                .orElseThrow(() -> new EntityNotFoundException(purchase.getProduct_id(), Product.class.getSimpleName()));
        prodRepo.save(purchasedProduct);
        return ResponseEntity.ok(purchAss.toModel(purchRepo.save(purchase)));
    }

    public ResponseEntity<RepresentationModel> cancelPurchase(@PathVariable Long id){

        Purchase purchase = purchRepo.findById(id)
                .orElseThrow(()-> new EntityNotFoundException(id, Purchase.class.getSimpleName()));

        if (purchase.getStatus() == Status.IN_PROGRESS){
            purchase.setStatus(Status.CANCELLED);
            return ResponseEntity.ok(purchAss.toModel(purchRepo.save(purchase)));
        }

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).
                body(new VndErrors.VndError("Method not allowed",
                        "You can't cancel a purchase that is in the " + purchase.getStatus() + " status"));
    }
}
