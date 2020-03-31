package com.example.mission23;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class PurchaseController {

    @Autowired
    PurchaseRepo purchaseRepo;

    @PostMapping(value = "/purchases")
    @ResponseBody
    public Purchase addToCart(@RequestBody Purchase purchase) {
        purchaseRepo.save(purchase);
        return purchase;
    }

    @DeleteMapping(value = "/purchases/{id}")
    public void removeFromCart(@PathVariable long id) {
        purchaseRepo.deleteById(id);
//        return "purchases";
    }

    @GetMapping(value = "/purchases")
    @ResponseBody
    public List<Purchase> getPurchases() {
        return purchaseRepo.findAll();
    }

    @PutMapping(value = "/purchases/{id}")
    public Purchase editPurchase(@RequestBody Purchase purchase, @PathVariable long id) {
        purchaseRepo.updateById(purchase.getProduct_id(), purchase.getPurchase_date(), id);
        return purchase;
    }

    @PutMapping(value = "/purchases/{id}/confirm")
    public void confirmPurchase(@PathVariable long id) {
        purchaseRepo.confirmPurchase(id);
//        return purchase;
    }
}
