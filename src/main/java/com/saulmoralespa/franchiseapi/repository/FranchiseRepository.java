package com.saulmoralespa.franchiseapi.repository;

import com.saulmoralespa.franchiseapi.model.Franchise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface FranchiseRepository extends JpaRepository<Franchise, Long> {}