package com.gaven.mission4.web.controller;

import com.gaven.mission4.data.Status;
import com.gaven.mission4.data.assembler.PurchaseModelAssembler;
import com.gaven.mission4.data.entity.Purchase;
import com.gaven.mission4.data.repository.PurchaseRepository;
import com.gaven.mission4.exception.EntityNotFoundException;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.apache.coyote.Response;
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
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class PurchaseController {

    private final PurchaseRepository repo;
    private final PurchaseModelAssembler assembler;

    PurchaseController(PurchaseRepository repo, PurchaseModelAssembler assembler) {
        this.repo = repo;
        this.assembler = assembler;
    }

    @GetMapping("/purchases")
    public CollectionModel<EntityModel<Purchase>> viewPurchases(){

        List<EntityModel<Purchase>> purchases = repo.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return new CollectionModel<>(purchases,
                linkTo(methodOn(PurchaseController.class).viewPurchases()).withSelfRel());
    }

    @GetMapping("/purchases/{id}")
    public EntityModel<Purchase> getOne(@PathVariable Long id){
        Purchase purchase = repo.findById(id)
                .orElseThrow(()-> new EntityNotFoundException(id, Purchase.class
                        .getSimpleName()));
        return assembler.toModel(purchase);
    }

    @PostMapping("/purchases")
    public ResponseEntity<?> addToCart(@RequestBody Purchase newPurchase) throws URISyntaxException {

        if(newPurchase.getDate()==null){
            newPurchase.setDate(new Date());
        }

        newPurchase.setStatus(Status.IN_PROGRESS);

        EntityModel<Purchase> entityModel=assembler.toModel(repo.save(newPurchase));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PutMapping("/purchases/{id}")
    public ResponseEntity<?> editPurchase(@RequestBody Purchase newPurchase, @PathVariable Long id) throws URISyntaxException{

        Purchase editedPurchase = repo.findById(id)
                .map(purchase -> {
                    purchase.setProduct_id(newPurchase.getProduct_id());
                    return repo.save(purchase);
                })
                .orElseGet(()->{
                    newPurchase.setPurchase_id(id);
                    return repo.save(newPurchase);
                });

        EntityModel<Purchase> entityModel = assembler.toModel(editedPurchase);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PutMapping("/purchases/{id}/complete")
    public ResponseEntity<?> completePurchase(@PathVariable Long id){

        Purchase purchase = repo.findById(id).orElseThrow(()-> new EntityNotFoundException(id, Purchase.class.getSimpleName()));

        if (purchase.getStatus() == Status.IN_PROGRESS){
            purchase.setStatus(Status.COMPLETED);
            return ResponseEntity.ok(assembler.toModel(repo.save(purchase)));
        }

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(new VndErrors.VndError("Method not allowed",
                        "You can't complete a purchase that is in the " + purchase.getStatus() + " status"));
    }

    @DeleteMapping("/purchases/{id}/cancel")
    public ResponseEntity<RepresentationModel> cancelPurchase(@PathVariable Long id){

        Purchase purchase = repo.findById(id)
                .orElseThrow(()-> new EntityNotFoundException(id, Purchase.class.getSimpleName()));

        if (purchase.getStatus() == Status.IN_PROGRESS){
            purchase.setStatus(Status.CANCELLED);
            return ResponseEntity.ok(assembler.toModel(repo.save(purchase)));
        }

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).
                body(new VndErrors.VndError("Method not allowed",
                        "You can't cancel a purchase that is in the " + purchase.getStatus() + " status"));
    }
}
