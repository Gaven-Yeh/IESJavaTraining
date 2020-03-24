package com.gaven.mission4.data.repository;

import com.gaven.mission4.data.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

}
