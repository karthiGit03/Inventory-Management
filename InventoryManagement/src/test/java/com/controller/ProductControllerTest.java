package com.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.dto.ProductRequest;
import com.dto.ProductResponse;
import com.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void addProduct_shouldReturnCreatedProduct() throws Exception {

        ProductRequest request = new ProductRequest();
        request.setName("Keyboard");
        request.setQuantity(10);
        request.setPrice(1500);
        request.setSupplier("Logitech");

        ProductResponse response = new ProductResponse();
        response.setId(1L);
        response.setName("Keyboard");
        response.setQuantity(10);
        response.setPrice(1500);
        response.setSupplier("Logitech");

        when(productService.addProduct(any(ProductRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Keyboard"));
    }

    @Test
    void getAllProducts_shouldReturnList() throws Exception {

        ProductResponse p1 = new ProductResponse();
        p1.setName("Mouse");

        ProductResponse p2 = new ProductResponse();
        p2.setName("Monitor");

        when(productService.getAllProducts()).thenReturn(List.of(p1, p2));

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));
    }

    @Test
    void getProductById_shouldReturnProduct() throws Exception {

        ProductResponse response = new ProductResponse();
        response.setId(1L);
        response.setName("Laptop");

        when(productService.getProductById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/products/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Laptop"));
    }

    @Test
    void deleteProduct_shouldReturnNoContent() throws Exception {

        doNothing().when(productService).deleteProduct(1L);

        mockMvc.perform(delete("/api/products/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(productService).deleteProduct(1L);
    }
}
