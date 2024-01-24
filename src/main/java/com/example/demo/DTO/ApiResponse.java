package com.example.demo.DTO;

import com.example.demo.Models.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApiResponse <T> {
    private int statusCode;
    private String message;
    private T payload;

    public void setPayload(List<Product> existingProducts){
    }
}
