package com.gaven.mission4.data.entity;

import com.gaven.mission4.data.Status;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
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
