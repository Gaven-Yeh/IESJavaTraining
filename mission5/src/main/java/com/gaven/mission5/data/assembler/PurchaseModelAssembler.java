package com.gaven.mission5.data.assembler;

import com.gaven.mission5.data.Status;
import com.gaven.mission5.data.entity.Purchase;
import com.gaven.mission5.web.controller.PurchaseController;
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
                linkTo(methodOn(PurchaseController.class).getOne(purchase.getPurchase_id())).withSelfRel(),
                linkTo(methodOn(PurchaseController.class).viewPurchases()).withRel("purchases"));

        if (purchase.getStatus() == Status.IN_PROGRESS) {
            purchaseModel.add(
                    linkTo(methodOn(PurchaseController.class).cancelPurchase(purchase.getPurchase_id())).withRel("cancel"));
            purchaseModel.add(
                    linkTo(methodOn(PurchaseController.class).completePurchase(purchase.getPurchase_id())).withRel("complete"));
        }

        return purchaseModel;
    }
}
