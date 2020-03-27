package com.gaven.mission5.data.repository;

import com.gaven.mission5.business.exception.EntityNotFoundException;
import com.gaven.mission5.data.Status;
import com.gaven.mission5.data.entity.Purchase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


@ExtendWith(SpringExtension.class)
@DataJpaTest
public class PurchaseRepositoryUnitTests {

    @Autowired
    private PurchaseRepository purchaseRepo;

    @Test
    public void testPurchaseSave() {
        Date date = new Date();
        Purchase purchase = new Purchase(1L, date, Status.IN_PROGRESS);
        Purchase result = purchaseRepo.save(purchase);

        assertThat(result, notNullValue());
        assertThat(purchase, is(result));
    }

    @Test
    public void testPurchaseGet() {
        Date date = new Date();
        Purchase purchase = new Purchase(1L, date, Status.IN_PROGRESS);
        purchaseRepo.save(purchase);
        Purchase result = purchaseRepo.findById(1L)
                .orElseThrow(() -> new EntityNotFoundException(1L, Purchase.class.getSimpleName()));

        assertThat(result, notNullValue());
        assertThat(purchase, is(result));
    }
}
