package com.graduate.hou.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.graduate.hou.dto.request.ReviewDTO;
import com.graduate.hou.entity.Review;
import com.graduate.hou.service.impl.ReviewServiceImpl;

@RestController
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    private ReviewServiceImpl reviewService;

    @PostMapping("/new")
    public ResponseEntity<Review> createNewReview(@RequestBody ReviewDTO reviewDTO) {
        Review review = reviewService.creatReview(reviewDTO);
        return ResponseEntity.ok(review);
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<Review>> getAllReview(){
        List<Review> reviews = reviewService.getAllReviews();
        return ResponseEntity.ok(reviews);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Review> updateReview (@PathVariable Long id, @RequestBody ReviewDTO reviewDTO){
        Review review = reviewService.updateReview(id,reviewDTO);
        return ResponseEntity.ok(review);
    }

    @DeleteMapping("/{id}")
    String deleteReview(@PathVariable Long id){
        reviewService.deleteReview(id);
        return "Xóa thành công";
    }
}
