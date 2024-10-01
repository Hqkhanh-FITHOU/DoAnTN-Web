package com.graduate.hou.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.graduate.hou.entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>{

}
