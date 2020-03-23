package com.example.mission23;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ProductRepo extends JpaRepository<Product, Long> {
}
