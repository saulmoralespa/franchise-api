package com.saulmoralespa.franchiseapi.dto;


import jakarta.validation.constraints.NotBlank;

public class FranchiseRequest {

    @NotBlank(message = "El nombre de la franquicia no puede estar vacío")
    private String name;

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }
}