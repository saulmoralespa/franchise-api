package com.saulmoralespa.franchiseapi.model;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BranchTest {

    @Test
    void testBranchCreation() {
        Branch branch = new Branch();
        branch.setId(1L);
        branch.setName("Test Branch");
        branch.setProducts(new ArrayList<>());

        assertEquals(1L, branch.getId());
        assertEquals("Test Branch", branch.getName());
        assertNotNull(branch.getProducts());
        assertTrue(branch.getProducts().isEmpty());
    }

    @Test
    void testBranchFranchiseRelationship() {
        Branch branch = new Branch();
        Franchise franchise = new Franchise();
        franchise.setName("Test Franchise");

        branch.setFranchise(franchise);
        franchise.getBranches().add(branch);

        assertEquals(franchise, branch.getFranchise());
        assertTrue(franchise.getBranches().contains(branch));
    }

    @Test
    void testAddProduct() {
        Branch branch = new Branch();
        Product product = new Product();
        product.setName("Test Product");
        
        branch.getProducts().add(product);
        product.setBranch(branch);

        assertEquals(1, branch.getProducts().size());
        assertTrue(branch.getProducts().contains(product));
        assertEquals(branch, product.getBranch());
    }
}
