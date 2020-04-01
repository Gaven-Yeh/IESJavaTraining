package com.gaven.mission7.assembler;

import com.gaven.mission7.Status;
import com.gaven.mission7.entity.Purchase;
import com.gaven.mission7.controller.PurchaseController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PurchaseModelAssembler implements RepresentationModelAssembler<Purchase, EntityModel<Purchase>> {

    @Override
    public EntityModel<Purchase> toModel(Purchase purchase) {

        EntityModel<Purchase> purchaseModel = new EntityModel<>(purchase,
                linkTo(methodOn(PurchaseController.class).getOne(purchase.getPurchase_id(), 0)).withSelfRel(),
                linkTo(methodOn(PurchaseController.class).viewPurchases(0)).withRel("purchases"));

        if (purchase.getStatus() == Status.IN_PROGRESS) {
            purchaseModel.add(
                    linkTo(methodOn(PurchaseController.class).cancelPurchase(purchase.getPurchase_id(),0)).withRel("cancel"));
            purchaseModel.add(
                    linkTo(methodOn(PurchaseController.class).completePurchase(purchase.getPurchase_id(),0)).withRel("complete"));
        }

        return purchaseModel;
    }
}
