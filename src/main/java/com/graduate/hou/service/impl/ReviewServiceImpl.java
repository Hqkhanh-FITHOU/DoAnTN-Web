package com.graduate.hou.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.graduate.hou.dto.request.ReviewDTO;
import com.graduate.hou.entity.Product;
import com.graduate.hou.entity.Review;
import com.graduate.hou.entity.User;
import com.graduate.hou.repository.ProductRepository;
import com.graduate.hou.repository.ReviewRepository;
import com.graduate.hou.repository.UsersRepository;
import com.graduate.hou.service.ReviewService;

@Service
public class ReviewServiceImpl implements ReviewService{

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    @Override
    public Review creatReview(ReviewDTO reviewDTO) {
        Review review = new Review();
        Optional<User> user = usersRepository.findById(reviewDTO.getUserId());
        Optional<Product> product = productRepository.findById(reviewDTO.getProductId());
        review.setUser(user.get());
        review.setComment(reviewDTO.getComment());
        review.setProduct(product.get());
        review.setRating(reviewDTO.getRating());
        
        return reviewRepository.save(review);
    }

    @Override
    public Review updateReview(Long id, ReviewDTO reviewDTO) {
        Optional<Review> reviewOptional = reviewRepository.findById(reviewDTO.getReviewId());
        Review reviewUpdate = reviewOptional.get();
        Optional<User> user = usersRepository.findById(reviewDTO.getUserId());
        Optional<Product> product = productRepository.findById(reviewDTO.getProductId());
        reviewUpdate.setUser(user.get());
        reviewUpdate.setComment(reviewDTO.getComment());
        reviewUpdate.setProduct(product.get());
        reviewUpdate.setRating(reviewDTO.getRating());
        
        return reviewRepository.save(reviewUpdate);
    }

    @Override
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }

}
