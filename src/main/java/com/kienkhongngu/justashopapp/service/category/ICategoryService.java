package com.kienkhongngu.justashopapp.service.category;
import com.kienkhongngu.justashopapp.entity.Category;

import java.util.List;

public interface ICategoryService {
    List<Category> getAllCategories();
    Category getCategoryById(Long id);
    Category addCategory(Category category);
    Category updateCategory(Category category,Long id);
    Category getCategoryByName(String name);
    void deleteCategoryById(Long id);

}
