package com.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dto.ProductRequest;
import com.dto.ProductResponse;
import com.entity.Product;
import com.exception.ResourceNotFoundException;
import com.repository.ProductRepository;
import com.service.impl.ProductServiceImpl;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void addProduct_shouldReturnProductResponse() {

        ProductRequest request = new ProductRequest();
        request.setName("Keyboard");
        request.setQuantity(10);
        request.setPrice(1500);
        request.setSupplier("Logitech");

        Product saved = new Product();
        saved.setId(1L);
        saved.setName("Keyboard");

        when(productRepository.save(any(Product.class))).thenReturn(saved);

        ProductResponse response = productService.addProduct(request);

        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getName()).isEqualTo("Keyboard");
    }

    @Test
    void getAllProducts_shouldReturnDtoList() {

        Product p1 = new Product();
        p1.setName("Mouse");

        Product p2 = new Product();
        p2.setName("Monitor");

        when(productRepository.findAll()).thenReturn(List.of(p1, p2));

        List<ProductResponse> responses = productService.getAllProducts();

        assertThat(responses).hasSize(2);
    }

    @Test
    void getProductById_shouldReturnProductResponse_whenFound() {

        Product product = new Product();
        product.setId(1L);
        product.setName("Laptop");

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        ProductResponse response = productService.getProductById(1L);

        assertThat(response.getName()).isEqualTo("Laptop");
    }

    @Test
    void getProductById_shouldThrowException_whenNotFound() {

        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        try {
            productService.getProductById(99L);
        } catch (ResourceNotFoundException ex) {
            assertThat(ex.getMessage()).contains("Product not found");
        }
    }
}
