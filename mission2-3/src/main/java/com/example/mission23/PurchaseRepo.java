package com.example.mission23;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;

public interface PurchaseRepo extends JpaRepository<Purchase, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE Purchase P SET P.product_id= :product_id, P.purchase_date= :date WHERE P.purchase_id= :id")
    void updateById(long product_id, Date date, long id);

    @Modifying
    @Transactional
    @Query("UPDATE Purchase P SET P.is_purchased=true WHERE P.purchase_id= :id")
    void confirmPurchase(long id);
}
