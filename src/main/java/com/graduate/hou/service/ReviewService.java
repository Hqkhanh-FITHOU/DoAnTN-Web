package com.graduate.hou.service;

import java.util.List;
import com.graduate.hou.dto.ReviewDTO;
import com.graduate.hou.entity.Review;

public interface ReviewService {

    List<Review> getAllReviews();

    Review creatReview(ReviewDTO reviewDTO);

    Review updateReview(Long id, ReviewDTO reviewDTO);

    void deleteReview(Long id);
}
