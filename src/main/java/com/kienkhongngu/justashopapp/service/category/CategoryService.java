package com.kienkhongngu.justashopapp.service.category;

import com.kienkhongngu.justashopapp.entity.Category;
import com.kienkhongngu.justashopapp.exception.AlreadyExistsException;
import com.kienkhongngu.justashopapp.exception.ResourceNotFoundException;
import com.kienkhongngu.justashopapp.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
    }

    @Override
    public Category addCategory(Category category) {
        if (categoryRepository.existsByName(category.getName())) {
            throw new AlreadyExistsException("Category already exists with name: " + category.getName());
        }
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Category category, Long id) {
        Category oldCategory = getCategoryById(id);
        oldCategory.setName(category.getName());
        return categoryRepository.save(oldCategory);
    }

    @Override
    public Category getCategoryByName(String name) {
        Category category = categoryRepository.findByName(name);
        if (category == null) {
            throw new ResourceNotFoundException("Category not found with name: " + name);
        }
        return category;

    }

    @Override
    public void deleteCategoryById(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Category not found with id: " + id);
        }
        categoryRepository.deleteById(id);
    }
}
