package com.graduate.hou.repository;

import com.graduate.hou.entity.Product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByName(String name);

    boolean existsByOrderItems_ProductProductId(Long productId);

    boolean existsByNameAndProductIdNot(String name, Long productId);
}
