package com.gaven.mission5.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
@AllArgsConstructor
public class Product {

    private @Id @GeneratedValue Long product_id;
//    private String name;
    private String type;
    private String instrument;
    private String brand;
    private int stock;

    public Product(){}

    public Product(String type, String instrument, String brand, int stock){
        this.type = type;
        this.instrument = instrument;
        this.brand = brand;
        this.stock = stock;
    }

    public String getName(){
        return this.type + " " + this.instrument;
    }

    public void setName(String name){
        String[] parts = name.split(" ");
        this.type = parts[0];
        this.instrument = parts[1];
    }
}
