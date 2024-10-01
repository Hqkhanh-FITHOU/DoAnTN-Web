package com.graduate.hou.controller.rest;

import com.graduate.hou.dto.AddressDTO;
import com.graduate.hou.dto.CategoryDTO;
import com.graduate.hou.entity.Address;
import com.graduate.hou.entity.Category;
import com.graduate.hou.service.AddressService;
import com.graduate.hou.service.impl.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryServiceImpl categoryService;

    @PostMapping
    Category createCategory(@RequestBody CategoryDTO categoryDTO){
        return categoryService.createCategory(categoryDTO);
    }

    @GetMapping
    List<Category> getAllCategory(){
        return categoryService.getAllCategory();
    }

    @PutMapping("/{id}")
    Category updateCategory (@PathVariable Long id, @RequestBody CategoryDTO categoryDTO){
        return categoryService.updateCategory(id, categoryDTO);
    }

    @DeleteMapping("/{id}")
    String deleteCategory(@PathVariable Long id){
        categoryService.deleteCategory(id);
        return "Xóa thành công";
    }
}
