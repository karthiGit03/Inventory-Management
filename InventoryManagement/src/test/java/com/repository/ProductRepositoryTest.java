package com.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.entity.Product;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void saveProduct_shouldPersistProduct() {
        // given
        Product product = new Product();
        product.setName("Keyboard");
        product.setQuantity(10);
        product.setPrice(1500);
        product.setSupplier("Logitech");

        // when
        Product savedProduct = productRepository.save(product);

        // then
        assertThat(savedProduct.getId()).isNotNull();
        assertThat(savedProduct.getName()).isEqualTo("Keyboard");
    }

    @Test
    void findById_shouldReturnProduct() {
        // given
        Product product = new Product();
        product.setName("Mouse");
        product.setQuantity(20);
        product.setPrice(800);
        product.setSupplier("Dell");

        Product saved = productRepository.save(product);

        // when
        Optional<Product> found = productRepository.findById(saved.getId());

        // then
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Mouse");
    }

    @Test
    void deleteById_shouldRemoveProduct() {
        // given
        Product product = new Product();
        product.setName("Monitor");
        product.setQuantity(5);
        product.setPrice(12000);
        product.setSupplier("Samsung");

        Product saved = productRepository.save(product);

        // when
        productRepository.deleteById(saved.getId());

        // then
        Optional<Product> deleted = productRepository.findById(saved.getId());
        assertThat(deleted).isEmpty();
    }
}
