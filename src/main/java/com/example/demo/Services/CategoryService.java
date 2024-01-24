package com.example.demo.Services;

import com.example.demo.DTO.ApiResponse;
import com.example.demo.Models.Category;
import com.example.demo.Repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category>getAllCategories() {
        return categoryRepository.findAll();
    }
    public List<Category> getCategoryByID(Long id){
        return categoryRepository.findById(id).stream().toList();
    }
    public Optional<Category> getCategoryByName(String name){
        return categoryRepository.getCategoryByName(name);
    }

    public ResponseEntity<ApiResponse<List<Category>>> addNewCategories(Category category){
        try {
            // checking if category name is exist
            if(isCategoryDuplicate(category.getName())){
                return null;
            }
             categoryRepository.save(category);

            return ResponseEntity.ok(new ApiResponse<>(201, "Category added", new ArrayList<>()));
        } catch (Exception e){
            return ResponseEntity.status(500).body(new ApiResponse<>(500, "server error", new ArrayList<>()));
        }
    }

    public ResponseEntity<ApiResponse<List<Category>>> updateCategory(Category updateCategory, Long id){
        try{
            Optional<Category> existingCategoryOptional = categoryRepository.findById(id);
            if (existingCategoryOptional.isPresent()){
                Category existingCategory = existingCategoryOptional.get();

                existingCategory.setName(updateCategory.getName());

                categoryRepository.save(existingCategory);
                return ResponseEntity.ok(new ApiResponse<>(200, "Category updated successfully", new ArrayList<>()));
            } else {
                return ResponseEntity.status(404).body(new ApiResponse<>(404, "Category not found", new ArrayList<>()));
            }
        } catch (Exception e){
            return ResponseEntity.status(500).body(new ApiResponse<>(500, "server error", new ArrayList<>()));
        }
    }

    public ResponseEntity<ApiResponse<List<Void>>> deleteCategory(Long id){
        try {
            Optional<Category> exisitingCategoryOptional = categoryRepository.findById(id);
            if(exisitingCategoryOptional.isPresent()){
                Category category = exisitingCategoryOptional.get();
                categoryRepository.deleteById(id);
                return ResponseEntity.ok(new ApiResponse<>(200, "Category Deleted successfully", new ArrayList<>()));
            }else {
                return ResponseEntity.status(404).body(new ApiResponse<>(404, "Category not found", new ArrayList<>()));
            }
        }catch (Exception e){
            return ResponseEntity.status(404).body(new ApiResponse<>(404, "An error occurred while deleting the category", new ArrayList<>()));
        }
    }

    private boolean isCategoryDuplicate(String categoryName) {
        return categoryRepository.existsByName(categoryName);
    }
}
