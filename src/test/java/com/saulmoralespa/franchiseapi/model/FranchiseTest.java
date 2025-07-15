package com.saulmoralespa.franchiseapi.model;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FranchiseTest {

    @Test
    void testFranchiseCreation() {
        Franchise franchise = new Franchise();
        franchise.setId(1L);
        franchise.setName("Test Franchise");
        franchise.setBranches(new ArrayList<>());

        assertEquals(1L, franchise.getId());
        assertEquals("Test Franchise", franchise.getName());
        assertNotNull(franchise.getBranches());
        assertTrue(franchise.getBranches().isEmpty());
    }

    @Test
    void testAddBranch() {
        Franchise franchise = new Franchise();
        Branch branch = new Branch();
        branch.setName("Test Branch");
        
        franchise.getBranches().add(branch);
        branch.setFranchise(franchise);

        assertEquals(1, franchise.getBranches().size());
        assertTrue(franchise.getBranches().contains(branch));
        assertEquals(franchise, branch.getFranchise());
    }
}
