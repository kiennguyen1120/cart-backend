package com.kienkhongngu.justashopapp.controller;


import com.kienkhongngu.justashopapp.entity.Category;
import com.kienkhongngu.justashopapp.exception.AlreadyExistsException;
import com.kienkhongngu.justashopapp.exception.ResourceNotFoundException;
import com.kienkhongngu.justashopapp.response.ApiResponse;
import com.kienkhongngu.justashopapp.service.category.CategoryService;
import com.kienkhongngu.justashopapp.service.category.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {

    private final ICategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllCategories() {
        try {
            List<Category> categories = categoryService.getAllCategories();
            return ResponseEntity.ok(new ApiResponse("All categories retrieved successfully", categories));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error", INTERNAL_SERVER_ERROR));
        }
    }


    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addCategory(@RequestBody Category name) {
        try {
            Category category = categoryService.addCategory(name);
            return ResponseEntity.ok(new ApiResponse("Category added successfully", category));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/categories/{id}/category")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long id) {
        try {
            Category category = categoryService.getCategoryById(id);
            return ResponseEntity.ok(new ApiResponse("Category retrieved successfully", category));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/{name}/category")
    public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String name) {
        try {
            Category category =  categoryService.getCategoryByName(name);
            return ResponseEntity.ok(new ApiResponse("Category retrieved successfully", category));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }


    @DeleteMapping("/categories/{id}/delete")
    public ResponseEntity<ApiResponse> deleteCategoryById(@PathVariable Long id) {
        try {
            categoryService.deleteCategoryById(id);
            return ResponseEntity.ok(new ApiResponse("Category deleted successfully", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }


    @PutMapping("/categories/{id}/update")
    public ResponseEntity<ApiResponse> updateCategoryById(@PathVariable Long id, @RequestBody Category category) {
        try {
            Category updatedCategory = categoryService.updateCategory(category,id);
            return ResponseEntity.ok(new ApiResponse("Category updated successfully", updatedCategory));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }



}
