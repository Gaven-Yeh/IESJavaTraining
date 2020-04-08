package com.gaven.dzone_flyway.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    private String name;

    @JsonIgnore
    @OneToMany (mappedBy="customer")
    private List<Contact> contacts;

}
