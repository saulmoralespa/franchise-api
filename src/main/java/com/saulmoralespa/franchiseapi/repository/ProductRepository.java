package com.saulmoralespa.franchiseapi.repository;

import com.saulmoralespa.franchiseapi.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByBranchFranchiseIdOrderByStockDesc(Long franchiseId);
}