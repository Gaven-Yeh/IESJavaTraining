package com.gaven.mission5.data.repository;

import com.gaven.mission5.data.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
