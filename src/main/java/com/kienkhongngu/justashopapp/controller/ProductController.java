package com.kienkhongngu.justashopapp.controller;


import com.kienkhongngu.justashopapp.dto.ProductDto;
import com.kienkhongngu.justashopapp.entity.Product;
import com.kienkhongngu.justashopapp.exception.ResourceNotFoundException;
import com.kienkhongngu.justashopapp.request.AddProductRequest;
import com.kienkhongngu.justashopapp.request.ProductUpdateRequest;
import com.kienkhongngu.justashopapp.response.ApiResponse;
import com.kienkhongngu.justashopapp.service.product.IProductService;
import com.kienkhongngu.justashopapp.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {

    private final IProductService productService;

    @RequestMapping("/get-all")
    public ResponseEntity<ApiResponse> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
        return ResponseEntity.ok(new ApiResponse("All products retrieved successfully", convertedProducts));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long id) {
        try {
            Product product = productService.getProductById(id);
            ProductDto productDto = productService.convertToDto(product);
            return ResponseEntity.ok(new ApiResponse("Product retrieved successfully", productDto));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }


    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product){
        try {
            Product newProduct = productService.addProduct(product);
            return ResponseEntity.ok(new ApiResponse("Product added successfully", newProduct));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PutMapping("/{productId}/update")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductUpdateRequest request, @PathVariable Long productId){
        try {
            Product updatedProduct = productService.updateProduct(request, productId);
            return ResponseEntity.ok(new ApiResponse("Product updated successfully", updatedProduct));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }


    @DeleteMapping("/{productId}/delete")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId){
        try {
            productService.deleteProductById(productId);
            return ResponseEntity.ok(new ApiResponse("Product deleted successfully", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));        }
    }

    @GetMapping("/by/brand-and-name")
    public ResponseEntity<ApiResponse> getProductsByBrandAndName(@RequestParam String brandName, @RequestParam String productName) {
        try {
            List<Product> products = productService.getProductsByBrandAndName(brandName, productName);
            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found", null));
            }
            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);

            return ResponseEntity.ok(new ApiResponse("Get product successfully", convertedProducts));

        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));

        }

    }


    @GetMapping("/by/category-and-brand")
    public ResponseEntity<ApiResponse> getProductsByCategoryAndBrand(@RequestParam String category, @RequestParam String brand) {
        try {
            List<Product> products = productService.getProductsByCategoryAndBrand(category, brand);
            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found", null));
            }
            return ResponseEntity.ok(new ApiResponse("Get product successfully", products));

        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));

        }

    }

    @GetMapping("/by/{name}")
    public ResponseEntity<ApiResponse> getProductsByName(@PathVariable String name) {
        try {
            List<Product> products = productService.getProductsByName(name);
            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found", null));
            }
            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
            return ResponseEntity.ok(new ApiResponse("Get product successfully", convertedProducts));

        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));

        }
    }


    @GetMapping("/by-brand")
    public ResponseEntity<ApiResponse> getProductsByBrand(@RequestParam String brand) {
        try {
            List<Product> products = productService.getProductsByBrand(brand);
            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found", null));
            }
            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
            return ResponseEntity.ok(new ApiResponse("Get product successfully", convertedProducts));

        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));

        }
    }

    @GetMapping("/by/{category}/all")
    public ResponseEntity<ApiResponse> getProductByCategory(@PathVariable String category) {
        try {
            List<Product> products = productService.getProductsByCategory(category);
            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found", null));
            }
            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);

            return ResponseEntity.ok(new ApiResponse("Get product successfully", convertedProducts));

        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));

        }
    }

    public ResponseEntity<ApiResponse> countProductsByBrandAndName(@RequestParam String brand, @RequestParam String name) {
        try {
            var count = productService.countProductsByBrandAndName(brand, name);
            return ResponseEntity.ok(new ApiResponse("Product count", count));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), INTERNAL_SERVER_ERROR));
        }
    }




}
