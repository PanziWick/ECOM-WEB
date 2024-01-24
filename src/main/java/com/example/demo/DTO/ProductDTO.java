package com.example.demo.DTO;

import lombok.Data;

@Data
public class ProductDTO {
    private Long productId;

    private String name;
    private String description;
    private Double price;

    private Long categoryID;
}
