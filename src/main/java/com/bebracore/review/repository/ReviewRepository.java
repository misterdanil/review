package com.bebracore.review.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bebracore.review.model.Review;

@Repository
public interface ReviewRepository extends MongoRepository<Review, String>, CustomReviewRepository {
	List<Review> findByProductId(String productId);

}
