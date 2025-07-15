package com.saulmoralespa.franchiseapi.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProductRequest {

    @NotBlank(message = "El nombre del producto no puede estar vacío")
    private String name;

    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock debe ser igual o mayor a 0")
    private Integer stock;

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public Integer getStock() { return stock; }

    public void setStock(Integer stock) { this.stock = stock; }
}