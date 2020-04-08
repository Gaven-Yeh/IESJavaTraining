package com.gaven.dzone_flyway.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Contact {
    @Id
    @GeneratedValue
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn (name="customer_id")
    private Customer customer;

    private String name;
    private String email;
    private String phone;
}
