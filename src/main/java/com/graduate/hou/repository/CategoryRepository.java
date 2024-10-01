package com.graduate.hou.repository;

import com.graduate.hou.dto.CategoryDTO;
import com.graduate.hou.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
