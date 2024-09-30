package com.kienkhongngu.justashopapp.service.product;

import com.kienkhongngu.justashopapp.dto.ImageDto;
import com.kienkhongngu.justashopapp.dto.ProductDto;
import com.kienkhongngu.justashopapp.entity.Image;
import com.kienkhongngu.justashopapp.entity.Product;
import com.kienkhongngu.justashopapp.entity.Category;
import com.kienkhongngu.justashopapp.exception.ProductNotFoundException;
import com.kienkhongngu.justashopapp.repository.CategoryRepository;
import com.kienkhongngu.justashopapp.repository.ImageRepository;
import com.kienkhongngu.justashopapp.repository.ProductRepository;
import com.kienkhongngu.justashopapp.request.AddProductRequest;
import com.kienkhongngu.justashopapp.request.ProductUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService  {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;
    private final ModelMapper modelMapper;

    @Override
    public Product addProduct(AddProductRequest request) {
        //check if product is founded in db
        //if yes, then set it as a new product category
        //if no, then create a new product and set its category
        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
                .orElseGet(() -> {
                    Category newCategory = new Category(request.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });
        request.setCategory(category);
        return productRepository.save(createProduct(request, category));
    }

    private Product createProduct(AddProductRequest request, Category category){
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getDescription(),
                request.getPrice(),
                request.getQuantity(),
                category

        );
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
    }

    @Override
    public void deleteProductById(long id) {
productRepository.findById(id)
        .ifPresentOrElse(productRepository::delete,() -> {
            throw new ProductNotFoundException("Product not found with id: " + id);
        });
    }

    @Override
    public Product updateProduct(ProductUpdateRequest request, Long productId) {
            return productRepository.findById(productId)
                    .map(existingProduct -> updateExistingProduct(existingProduct, request))
                    .map(productRepository::save)
                    .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + productId));

    }

    private Product updateExistingProduct(Product existingProduct, ProductUpdateRequest request) {
            existingProduct.setName(request.getName());
            existingProduct.setBrand(request.getBrand());
            existingProduct.setPrice(request.getPrice());
            existingProduct.setQuantity(request.getQuantity());
            existingProduct.setDescription(request.getDescription());

            Category category = categoryRepository.findByName(request.getCategory().getName());
            existingProduct.setCategory(category);
            return existingProduct;
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findAllByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);
    }

    @Override
    public ProductDto convertToDto(Product product) {
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        List<Image> images = imageRepository.findByProductId(product.getId());
        List<ImageDto> imageDtos = images.stream()
                .map(image -> modelMapper.map(image, ImageDto.class))
                .toList();
        productDto.setImages(imageDtos);
        return productDto;
    }
    @Override
    public List<ProductDto> getConvertedProducts(List<Product> products) {
        return products.stream()
               .map(this::convertToDto)
               .collect(Collectors.toList());
    }
}
