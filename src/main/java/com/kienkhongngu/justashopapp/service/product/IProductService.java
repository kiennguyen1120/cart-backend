package com.kienkhongngu.justashopapp.service.product;

import com.kienkhongngu.justashopapp.dto.ProductDto;
import com.kienkhongngu.justashopapp.entity.Product;
import com.kienkhongngu.justashopapp.request.AddProductRequest;
import com.kienkhongngu.justashopapp.request.ProductUpdateRequest;

import java.util.List;

public interface IProductService {
    Product addProduct(AddProductRequest product);
    List<Product> getAllProducts();
    Product getProductById(Long id);
    void deleteProductById(long id);
    Product updateProduct(ProductUpdateRequest product, Long productId);
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByBrandAndName(String brand, String name);
//    Long countProductsByCategory(String category);
    Long countProductsByBrandAndName(String brand, String name);
    ProductDto convertToDto(Product product);
    List<ProductDto> getConvertedProducts(List<Product> products);
}
