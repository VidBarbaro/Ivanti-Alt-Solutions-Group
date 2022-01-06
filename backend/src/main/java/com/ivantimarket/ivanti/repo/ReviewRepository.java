package com.ivantimarket.ivanti.repo;


import com.ivantimarket.ivanti.model.Review;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends MongoRepository<Review, Integer> {

    Review findById(long id);
}
