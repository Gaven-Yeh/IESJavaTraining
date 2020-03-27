package com.gaven.mission5.data.entity;

import com.gaven.mission5.data.Status;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
public class Purchase {

    @Id
    @GeneratedValue
    private Long purchase_id;
    private Long product_id;
    private Date date;
    private Status status;

    public Purchase(){}

    public Purchase(Long product_id, Date date, Status status){
        this.product_id = product_id;
        this.date = date;
        this.status = status;
    }
}
