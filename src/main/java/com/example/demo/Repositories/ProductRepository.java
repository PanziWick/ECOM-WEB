package com.example.demo.Repositories;

import com.example.demo.Models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository <Product, Long> {
    List<Product> findAllProductsByCategoryId(Long categoryId);
    boolean existsByName(String name);
}
