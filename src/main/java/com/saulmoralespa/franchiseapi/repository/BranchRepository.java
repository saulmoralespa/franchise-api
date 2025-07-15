package com.saulmoralespa.franchiseapi.repository;

import com.saulmoralespa.franchiseapi.model.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface BranchRepository extends JpaRepository<Branch, Long> {}