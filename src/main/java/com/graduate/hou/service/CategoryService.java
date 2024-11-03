package com.graduate.hou.service;


import com.graduate.hou.dto.request.CategoryDTO;
import com.graduate.hou.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategory();
    Category getCategory(Long id);
    Category createCategory(CategoryDTO categoryDTO);
    boolean updateCategory(Long id, CategoryDTO categoryDTO);
    boolean deleteCategory(Long id);
}
