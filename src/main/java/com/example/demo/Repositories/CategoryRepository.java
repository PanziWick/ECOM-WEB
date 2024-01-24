package com.example.demo.Repositories;

import com.example.demo.Models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category>getCategoryByName(String name);
    boolean existsByName(String name);
}
