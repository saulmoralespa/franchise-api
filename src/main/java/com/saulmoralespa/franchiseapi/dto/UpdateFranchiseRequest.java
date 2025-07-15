package com.saulmoralespa.franchiseapi.dto;

import jakarta.validation.constraints.NotBlank;

public class UpdateFranchiseRequest {

    @NotBlank(message = "El nombre no puede estar vacío")
    private String name;

    // Getters y Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}