package com.saulmoralespa.franchiseapi.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void testProductCreation() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setStock(10);

        assertEquals(1L, product.getId());
        assertEquals("Test Product", product.getName());
        assertEquals(10, product.getStock());
    }

    @Test
    void testProductBranchRelationship() {
        Product product = new Product();
        Branch branch = new Branch();
        branch.setName("Test Branch");

        product.setBranch(branch);
        branch.getProducts().add(product);

        assertEquals(branch, product.getBranch());
        assertTrue(branch.getProducts().contains(product));
    }

    @Test
    void testUpdateStock() {
        Product product = new Product();
        product.setStock(10);
        
        product.setStock(20);
        
        assertEquals(20, product.getStock());
    }
}
