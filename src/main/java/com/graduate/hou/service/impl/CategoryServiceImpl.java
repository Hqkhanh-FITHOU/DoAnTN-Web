package com.graduate.hou.service.impl;

import com.graduate.hou.dto.request.CategoryDTO;
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
        // category.setCreatedAt(categoryDTO.getCreatedAt());
        // category.setUpdatedAt(categoryDTO.getUpdatedAt());

        return categoryRepository.save(category);
    }

    @Override
    public boolean updateCategory(Long id, CategoryDTO categoryDTO) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        Category category = optionalCategory.get();
        if(category != null){
            category.setName(categoryDTO.getName());
            category.setDescription(categoryDTO.getDescription());
            try {
                categoryRepository.save(category);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).get();
        if(category != null){
            if(!category.getProducts().isEmpty()){
                return false; //không xóa danh mục đã có món ăn trực thuộc
            }
            try {
                categoryRepository.deleteById(id);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    @Override
    public Category getCategory(Long id) {
        return categoryRepository.findById(id).get();
    }
}
