package com.graduate.hou.service;


import com.graduate.hou.dto.request.CategoryDTO;
import com.graduate.hou.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategory();
    Category createCategory(CategoryDTO categoryDTO);
    Category updateCategory(Long id, CategoryDTO categoryDTO);
    void deleteCategory(Long id);
}
