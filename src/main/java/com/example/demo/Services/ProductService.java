package com.example.demo.Services;

import com.example.demo.DTO.ApiResponse;
import com.example.demo.DTO.ProductDTO;
import com.example.demo.Models.Category;
import com.example.demo.Models.Product;
import com.example.demo.Repositories.CategoryRepository;
import com.example.demo.Repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductByID(Long productId){
        return productRepository.findById(productId);
    }

    public List<Product> getProductsByCategory(Long categoryId){
        return productRepository.findAllProductsByCategoryId(categoryId);
    }

    public ResponseEntity<ApiResponse<List<Product>>> addNewProducts (ProductDTO productDTO){
        try {
            List<Product> productList = new ArrayList<>();
            if (isProductDuplicate(productDTO.getName())){
                return ResponseEntity.badRequest().body(new ApiResponse<>(400, "Product with same name already exists", new ArrayList<>()));
            }
            Category category = categoryRepository.findById(productDTO.getCategoryID())
                            .orElseThrow(()-> new RuntimeException("category not found"));

            Product product = new Product();
            product.setName(productDTO.getName());
            product.setDescription(productDTO.getDescription());
            product.setPrice(productDTO.getPrice());
            product.setCategory(category);

            productList.add(product);
            productRepository.save((product));
            return ResponseEntity.ok(new ApiResponse<>(201, "Product Added", productList));
        }catch (Exception e){
            return ResponseEntity.status(500).body(new ApiResponse<>(500, e.getMessage(), new ArrayList<>()));
        }
    }

    public ResponseEntity<ApiResponse<List<Product>>> updateProduct(Product updateproduct,Long id){
        try{
            Optional<Product> existingProductOptional = productRepository.findById(id);
            if (existingProductOptional.isPresent()){
                Product existingProduct = existingProductOptional.get();

                existingProduct.setName(updateproduct.getName());
                existingProduct.setDescription(updateproduct.getDescription());
                existingProduct.setPrice(updateproduct.getPrice());

                productRepository.save(existingProduct);
                return ResponseEntity.ok(new ApiResponse<>(200, "Product updated successfully", new ArrayList<>()));
            }else {
                return ResponseEntity.status(404).body(new ApiResponse<>(404, "Product not found", new ArrayList<>()));
            }
        }catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(500, "server error", new ArrayList<>()));
        }
    }

    public ResponseEntity<ApiResponse<List<Void>>> deleteProduct(Long id){
        try {
            Optional<Product> exisitingProductOptional = productRepository.findById(id);
            if (exisitingProductOptional.isPresent()) {
                Product product = exisitingProductOptional.get();
                productRepository.deleteById(id);
                return ResponseEntity.ok(new ApiResponse<>(200, "Product deleted successfully", new ArrayList<>()));
            }else {
                return ResponseEntity.status(404).body(new ApiResponse<>(404, "Product not found", new ArrayList<>()));
            }
        }catch (Exception e){
            return ResponseEntity.status(500).body(new ApiResponse<>(500, "An error occurred while deleting the product", new ArrayList<>()));
        }
    }


    private boolean isProductDuplicate(String productName) {
        return productRepository.existsByName(productName);
    }

}
