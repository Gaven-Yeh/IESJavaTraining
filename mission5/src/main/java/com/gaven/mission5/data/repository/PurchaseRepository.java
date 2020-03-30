package com.gaven.mission5.data.repository;

import com.gaven.mission5.data.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

}
