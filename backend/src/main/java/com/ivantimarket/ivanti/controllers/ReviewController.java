package com.ivantimarket.ivanti.controllers;


import com.ivantimarket.ivanti.model.Review;
import com.ivantimarket.ivanti.service.ReviewService;
import com.ivantimarket.ivanti.service.SequenceGeneratorService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/api")
public class ReviewController {

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    private final ReviewService reviewService;

    private final SequenceGeneratorService sequenceGeneratorService;

    public ReviewController(ReviewService reviewService, SequenceGeneratorService sequenceGeneratorService) {
        this.reviewService = reviewService;
        this.sequenceGeneratorService = sequenceGeneratorService;
    }

    @GetMapping("/review/{id}")
    public ResponseEntity<Review> getReview(@PathVariable("id")int id) {
        LOG.info("Getting review with ID : {}", id);
        return ResponseEntity.ok().body(reviewService.getReviewById(id));
    }

    @PostMapping("/review/create")
    public ResponseEntity<Review> createReview(@RequestBody Review review) {
        LOG.info("Saving review");
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/review/create").toUriString());
        review.setId(sequenceGeneratorService.generateSequence(Review.SEQUENCE_NAME));
        return ResponseEntity.created(uri).body(reviewService.createReview(review));
    }

    @GetMapping("/reviews/user/{userId}")
    public ResponseEntity<List<Review>> getReviewsByUserId(@PathVariable("userId")int userId) {
        LOG.info("Getting all reviews by user with ID : {}", userId);
        return ResponseEntity.ok().body(reviewService.getReviewsByUserId(userId));
    }

    @GetMapping("/reviews/package/{packageId}")
    public ResponseEntity<List<Review>> getReviewsByPackageId(@PathVariable("packageId")int packageId) {
        LOG.info("Getting all reviews from package with ID : {}", packageId);
        return ResponseEntity.ok().body(reviewService.getReviewsByPackageId(packageId));
    }

    @PostMapping("/review/delete/{id}")
    public ResponseEntity<Boolean> deleteReviewById(@PathVariable("id")int id) {
        LOG.info("Deleting review with ID : {}", id);
        return ResponseEntity.ok().body(reviewService.deleteReview(id));
    }

    @PostMapping("/review/update/{id}/{rating}")
    public ResponseEntity<Review> updateReview(@PathVariable("id")int id, @PathVariable("rating")int rating) {
        LOG.info("Updating rating of package with ID : {} to : {}",id,rating);
        return ResponseEntity.ok().body(reviewService.updateReview(id,rating));
    }

    @GetMapping("/reviews/packageAverageRating/{packageId}")
    public double getAverageRatingOfPackage(@PathVariable("packageId")int packageId) {
        return reviewService.calculateAverageRatingOfPackage(packageId);
    }

    @GetMapping("/reviews/nrReviewsWithRating/{packageId}/{rating}")
    public int getNrReviewsOfPackageRating(@PathVariable("packageId")int packageId, @PathVariable("rating") int rating) {
        return reviewService.getNrReviewsOfPackageByRating(rating, packageId);
    }
}
