package com.saulmoralespa.franchiseapi.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import com.saulmoralespa.franchiseapi.model.Franchise;
import com.saulmoralespa.franchiseapi.model.Branch;
import com.saulmoralespa.franchiseapi.model.Product;
import com.saulmoralespa.franchiseapi.repository.BranchRepository;
import com.saulmoralespa.franchiseapi.repository.FranchiseRepository;
import com.saulmoralespa.franchiseapi.repository.ProductRepository;
import com.saulmoralespa.franchiseapi.dto.TopProductResponse;

@Service
public class FranchiseService {

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private FranchiseRepository franchiseRepository;

    @Autowired
    private ProductRepository productRepository;

    public Franchise addFranchise(String name) {
        Franchise franchise = new Franchise();
        franchise.setName(name);
        return franchiseRepository.save(franchise);
    }

    public void updateFranchiseName(Long franchiseId, String newName) {
        Franchise franchise = franchiseRepository.findById(franchiseId)
                .orElseThrow(() -> new RuntimeException("Franquicia no encontrada"));

        franchise.setName(newName);
        franchiseRepository.save(franchise);
    }

    public Franchise addBranch(Long franchiseId, String nameBranch) {
        Franchise franchise = franchiseRepository.findById(franchiseId)
            .orElseThrow(() -> new RuntimeException("No existe franquicia"));
        Branch branch = new Branch();
        branch.setName(nameBranch);
        franchise.getBranches().add(branch);
        return franchiseRepository.save(franchise);
    }

    public void updateBranchName(Long franchiseId, Long branchId, String newName) {
        Franchise franchise = franchiseRepository.findById(franchiseId)
                .orElseThrow(() -> new RuntimeException("Franquicia no encontrada"));
        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));
        if (!franchise.getBranches().contains(branch)) {
            throw new RuntimeException("La sucursal no pertenece a esta franquicia");
        }

        branch.setName(newName);
        branchRepository.save(branch);
    }

    public Franchise addProduct(Long franchiseId, Long branchId, String nameProduct, Integer stock) {
        Franchise franchise = franchiseRepository.findById(franchiseId)
            .orElseThrow(() -> new RuntimeException("No existe franquicia"));
        Branch branch = franchise.getBranches().stream()
            .filter(s -> s.getId().equals(branchId))
            .findFirst().orElseThrow(() -> new RuntimeException("No existe sucursal"));
        Product product = new Product();
        product.setName(nameProduct);
        product.setStock(stock);
        branch.getProducts().add(product);
        return franchiseRepository.save(franchise);
    }

    public List<TopProductResponse> getTopProductsByBranch(Long franchiseId) {
        Franchise franchise = franchiseRepository.findById(franchiseId)
                .orElseThrow(() -> new RuntimeException("Franquicia no encontrada"));

        List<TopProductResponse> result = new ArrayList<>();

        for (Branch branch : franchise.getBranches()) {
            Optional<Product> topProduct = branch.getProducts().stream()
                    .max(Comparator.comparing(Product::getStock));

            topProduct.ifPresent(product -> {
                TopProductResponse response = new TopProductResponse();
                response.setBranchId(branch.getId());
                response.setBranchName(branch.getName());
                response.setProductId(product.getId());
                response.setProductName(product.getName());
                response.setStock(product.getStock());

                result.add(response);
            });
        }

        return result;
    }

    public void updateProductStock(Long franchiseId, Long branchId, Long productId, Integer stock) {
        Franchise franchise = franchiseRepository.findById(franchiseId)
                .orElseThrow(() -> new RuntimeException("Franquicia no encontrada"));
        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        product.setStock(stock);
        productRepository.save(product);
    }

    public void deleteProduct(Long franchiseId, Long branchId, Long productId) {
        Franchise franchise = franchiseRepository.findById(franchiseId)
                .orElseThrow(() -> new RuntimeException("Franquicia no encontrada"));

        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        branch.getProducts().remove(product);
        productRepository.delete(product);
        branchRepository.save(branch);
    }
}