package com.ivantimarket.ivanti.service;


import com.ivantimarket.ivanti.model.Review;
import com.ivantimarket.ivanti.repo.ReviewRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private static final DecimalFormat df = new DecimalFormat("0.00");

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

    public double calculateAverageRatingOfPackage(long packageId)
    {
        List<Review> reviews = this.getReviewsByPackageId(packageId);
        double sumOfRatings = 0;
        double nrRatings = reviews.size();
        if(nrRatings == 0)
        {
            return 0;
        }
        else
        {
            for(Review r : reviews)
            {
                sumOfRatings += r.getRating();
            }
        }
        return round((sumOfRatings / nrRatings), 2);
    }

    public int getNrReviewsOfPackageByRating(int rating, long packageId)
    {
        int nrReviews = 0;
        for(Review r : this.getReviewsByPackageId(packageId))
        {
            if(r.getRating() == rating)
            {
                nrReviews++;
            }
        }
        return nrReviews;
    }

    private static double round(double value, int places)
    {
        if (places < 0) throw new IllegalArgumentException();
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
