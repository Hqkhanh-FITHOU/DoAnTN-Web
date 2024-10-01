package com.graduate.hou.service.impl;

import com.graduate.hou.dto.CategoryDTO;
import com.graduate.hou.entity.Category;
import com.graduate.hou.repository.CategoryRepository;
import com.graduate.hou.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public Category createCategory(CategoryDTO categoryDTO) {
        Category category = new Category();

        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        category.setCreatedAt(categoryDTO.getCreatedAt());
        category.setUpdatedAt(categoryDTO.getUpdatedAt());

        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Long id, CategoryDTO categoryDTO) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        Category category = optionalCategory.get();

        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        category.setCreatedAt(categoryDTO.getCreatedAt());
        category.setUpdatedAt(categoryDTO.getUpdatedAt());

        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
