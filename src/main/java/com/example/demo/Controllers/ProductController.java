package com.example.demo.Controllers;

import com.example.demo.DTO.ApiResponse;
import com.example.demo.DTO.ProductDTO;
import com.example.demo.Models.Product;
import com.example.demo.Repositories.ProductRepository;
import com.example.demo.Services.CategoryService;
import com.example.demo.Services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Product>>> getAllProducts() {
        try {
            List<Product> products = productService.getAllProducts();
            return ResponseEntity.ok(new ApiResponse<>(200, "Product retrieved", products));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(500,"An error occurred while retrieving the products", new ArrayList<>()));
        }
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long productId) {
        try {
            //List<Product> product = productService.getProductByID(id).stream().toList();
            return ResponseEntity.ok(new ApiResponse<>(200, "Product retrieved", productService.getProductByID(productId)));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(500, e.getMessage(), new ArrayList<>()));
        }
    }

    //To check the End-Point from request parameter(params)
    @GetMapping("/getcategory")
    public ResponseEntity<ApiResponse<List<Product>>> getProductsByCategory1(@RequestParam(name = "categoryName") String categoryName) {
        try {
            var category = categoryService.getCategoryByName(categoryName);
            List<Product> products = productService.getProductsByCategory(category.get().getId()).stream().toList();
            return ResponseEntity.ok(new ApiResponse<>(200, "Product retrieved", products));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(500,
                    e.getMessage().toString(),
                    new ArrayList<>()));
        }
    }

    //To check the End-Point from Path Variable(URL)
    @GetMapping("/getcategory/{categoryName}")
    public ResponseEntity<ApiResponse<List<Product>>> getProductsByCategory2(@PathVariable String categoryName) {
        try {
            var category = categoryService.getCategoryByName(categoryName);
            List<Product> products = productService.getProductsByCategory(category.get().getId()).stream().toList();
            return ResponseEntity.ok(new ApiResponse<>(200, "Product retrieved", products));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(500,
                    e.getMessage().toString(),
                    new ArrayList<>()));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<List<Product>>> createProduct(@RequestBody ProductDTO productDTO) {
        try {
            return productService.addNewProducts(productDTO);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(500, "An error occurred while retrieving the products", new ArrayList<>()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<List<Product>>> updateProduct(@RequestBody Product product, @PathVariable Long id) {
        try {
            return productService.updateProduct(product,id);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(500, "An error occurred while retrieving the products", new ArrayList<>()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<List<Void>>> deleteProduct(@PathVariable Long id) {
        try {
            return productService.deleteProduct(id);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(500, "An error occured while retrieving the products", new ArrayList<>()));
        }
    }
}
