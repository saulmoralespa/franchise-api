package com.saulmoralespa.franchiseapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
import java.util.List;

import com.saulmoralespa.franchiseapi.model.Franchise;
import com.saulmoralespa.franchiseapi.service.FranchiseService;
import com.saulmoralespa.franchiseapi.dto.BranchRequest;
import com.saulmoralespa.franchiseapi.dto.FranchiseRequest;
import com.saulmoralespa.franchiseapi.dto.ProductRequest;
import com.saulmoralespa.franchiseapi.dto.UpdateBranchRequest;
import com.saulmoralespa.franchiseapi.dto.UpdateStockRequest;
import com.saulmoralespa.franchiseapi.dto.UpdateFranchiseRequest;
import com.saulmoralespa.franchiseapi.dto.TopProductResponse;


@RestController
@RequestMapping("/api/franchises")
public class FranchiseController {

    @Autowired
    private FranchiseService franchiseService;

    @PostMapping
    public ResponseEntity<Franchise> addFranchise(
            @Valid @RequestBody FranchiseRequest request) {
        return ResponseEntity.ok(franchiseService.addFranchise(request.getName()));
    }

    @PutMapping("/{franchiseId}")
    public ResponseEntity<Void> updateFranchiseName(
        @PathVariable Long franchiseId,
        @Valid @RequestBody UpdateFranchiseRequest request) {
        franchiseService.updateFranchiseName(franchiseId, request.getName());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{franchiseId}/branches")
    public ResponseEntity<Franchise> addBranch(
            @PathVariable Long franchiseId,
            @Valid @RequestBody BranchRequest request) {
        return ResponseEntity.ok(franchiseService.addBranch(franchiseId, request.getName()));
    }

    @PutMapping("/{franchiseId}/branches/{branchId}")
    public ResponseEntity<Void> updateBranchName(
        @PathVariable Long franchiseId,
        @PathVariable Long branchId,
        @Valid @RequestBody UpdateBranchRequest request) {

        franchiseService.updateBranchName(franchiseId, branchId, request.getName());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{franchiseId}/{branchId}/product")
    public ResponseEntity<Franchise> addProduct(
            @PathVariable Long franchiseId,
            @PathVariable Long branchId,
            @Valid @RequestBody ProductRequest request) {
        return ResponseEntity.ok(franchiseService.addProduct(
            franchiseId, 
            branchId, 
            request.getName(),
            request.getStock()
        ));
    }

    @GetMapping("/{franchiseId}/branches/top-products")
    public ResponseEntity<List<TopProductResponse>> getTopProductsByBranch(
        @PathVariable Long franchiseId) {

        List<TopProductResponse> topProducts = franchiseService.getTopProductsByBranch(franchiseId);
        return ResponseEntity.ok(topProducts);
    }

    @PutMapping("/{franchiseId}/branches/{branchId}/products/{productId}/stock")
    public ResponseEntity<Void> updateProductStock(
            @PathVariable Long franchiseId,
            @PathVariable Long branchId,
            @PathVariable Long productId,
            @Valid @RequestBody UpdateStockRequest request) {

        franchiseService.updateProductStock(franchiseId, branchId, productId, request.getStock());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{franchiseId}/branches/{branchId}/products/{productId}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable Long franchiseId,
            @PathVariable Long branchId,
            @PathVariable Long productId) {

        franchiseService.deleteProduct(franchiseId, branchId, productId);
        return ResponseEntity.noContent().build();
    }
}