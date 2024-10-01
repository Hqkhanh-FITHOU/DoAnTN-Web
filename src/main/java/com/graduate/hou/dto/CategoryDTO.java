package com.graduate.hou.dto;

import com.graduate.hou.entity.Product;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.security.Timestamp;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
    private Long categoryId;

    private String name;

    private String description;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    private List<Product> products;
}
