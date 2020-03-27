package com.gaven.mission5.data.repository;

import com.gaven.mission5.business.exception.EntityNotFoundException;
import com.gaven.mission5.data.entity.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


@ExtendWith(SpringExtension.class)
@DataJpaTest
public class ProductRepositoryUnitTests {

    @Autowired
    private ProductRepository productRepo;
    
    @Test
    public void testProductSave() {
        Product product = new Product("Classical", "Guitar", "Cordoba", 3);
        Product result = productRepo.save(product);

        assertThat(result, notNullValue());
        assertThat(product, is(result));
    }

    @Test
    public void testProductGet() {
        Product product = new Product(1L,"Classical", "Guitar", "Cordoba", 3);
        productRepo.save(product);
        Product result = productRepo.findById(1L)
                .orElseThrow(() -> new EntityNotFoundException(1L, Product.class.getSimpleName()));

        assertThat(result, notNullValue());
        assertThat(product, is(result));
    }

    @Test
    public void testDelete(){
        Product product = new Product(1L,"Classical", "Guitar", "Cordoba", 3);
        productRepo.save(product);
        productRepo.deleteById(1L);

        Assertions.assertThrows(EntityNotFoundException.class,
                ()->{
            productRepo.findById(1L).orElseThrow(() -> new EntityNotFoundException(1L, Product.class.getSimpleName()));
        });
    }
}
