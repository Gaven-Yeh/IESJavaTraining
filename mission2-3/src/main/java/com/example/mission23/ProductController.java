package com.example.mission23;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    ProductRepo productRepo;

    @GetMapping(value = "/products")
    public List<Product> getProducts() {
        return productRepo.findAll();
    }
}
