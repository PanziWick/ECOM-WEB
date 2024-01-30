package com.example.demo.Controllers;

import com.example.demo.DTO.ApiResponse;
import com.example.demo.Models.Category;
import com.example.demo.Repositories.CategoryRepository;
import com.example.demo.Services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryService categoryService;


    @GetMapping
    public ResponseEntity<ApiResponse<List<Category>>> getAllCategories() {
        try {
            List<Category> categories = categoryService.getAllCategories();
            return ResponseEntity.ok(new ApiResponse<>(200, "Category retrieved", categories));
        } catch(Exception e){
            return ResponseEntity.status(500).body(new ApiResponse<>(500, "An error occurred while retrieving the categories", new ArrayList<>()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<List<Category>>> getCategoryById(@PathVariable Long id) {
        try {
            List<Category> categories = categoryService.getCategoryByID(id);
            return ResponseEntity.ok(new ApiResponse<>(200, "Category retrieved",categories));
        } catch (Exception e){
            return ResponseEntity.status(500).body(new ApiResponse<>(500, "An error occured while retrieving the category", new ArrayList<>()));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<List<Category>>>createCategory(@RequestBody Category category) {
        try {
            return categoryService.addNewCategories(category);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(500,"An error occurred while retrieving the categories", new ArrayList<>()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<List<Category>>>updateCategory(@RequestBody Category category, @PathVariable Long id) {
        try {
            return categoryService.updateCategory(category,id);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(500, "An error occurred while retrieving the categories", new ArrayList<>()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<List<Void>>> deleteCategory(@PathVariable Long id) {
        try {
            return categoryService.deleteCategory(id);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(500, "An error occurred while retrieving the categories", new ArrayList<>()));
        }
    }
}
