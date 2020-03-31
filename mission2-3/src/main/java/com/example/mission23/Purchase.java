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
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Purchase {
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private long purchase_id;
    private long product_id;
    private Date purchase_date;
    private boolean is_purchased;

    public Purchase(long purchase_id, long product_id, Date purchase_date, boolean is_purchased) {
        this.purchase_id = purchase_id;
        this.product_id = product_id;
        this.purchase_date = purchase_date;
        this.is_purchased = is_purchased;
    }
}
