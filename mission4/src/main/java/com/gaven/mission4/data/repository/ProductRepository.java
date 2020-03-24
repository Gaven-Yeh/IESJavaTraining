package com.gaven.mission4.data.repository;

import com.gaven.mission4.data.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
