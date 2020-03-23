package com.example.mission23;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter @Setter @NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long product_id;
    private String product_name;
    private String brand;
    private int stock;

    public Product(long product_id, String product_name, String brand, int stock) {
        this.product_id = product_id;
        this.product_name =product_name;
        this.brand = brand;
        this.stock = stock;
    }
}
