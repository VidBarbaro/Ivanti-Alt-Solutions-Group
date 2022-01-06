package com.ivantimarket.ivanti.service;


import com.ivantimarket.ivanti.model.Review;
import com.ivantimarket.ivanti.repo.ReviewRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public Review getReviewById(long id) {
        return reviewRepository.findById(id);
    }

    public List<Review> getReviewsByUserId(long userId) {
        List<Review> temp = reviewRepository.findAll();
        List<Review> temp1 = new ArrayList<>();
        for (Review x : temp) {
            if (x.getUserId() == userId) {
                temp1.add(x);
            }
        }
        return temp1;
    }

    public List<Review> getReviewsByPackageId(long packageId) {
        List<Review> temp = reviewRepository.findAll();
        List<Review> temp1 = new ArrayList<>();
        for (Review x : temp) {
            if (x.getPackageId() == packageId) {
                temp1.add(x);
            }
        }
        return temp1;
    }

    public Review createReview(Review review) {
        return reviewRepository.save(review);
    }

    public boolean deleteReview(long id) {
        Review review = reviewRepository.findById(id);
        if (review != null) {
            reviewRepository.delete(review);
            return true;
        }
        return false;
    }

    public Review updateReview(long id, int rating) {
        Review review = reviewRepository.findById(id);
        if (review!=null) {
            review.setRating(rating);
            return reviewRepository.save(review);
        }
        return null;
    }
}
