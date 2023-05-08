package com.bebracore.review.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.bebracore.review.model.Review;
import com.bebracore.review.repository.CustomReviewRepository;

@Repository
public class ReviewRepositoryImpl implements CustomReviewRepository {
	@Autowired
	private MongoOperations mongoOperations;

	@Override
	public boolean existsByLike(String reviewId, String userId) {
		Query query = new Query();

		query.addCriteria(Criteria.where("id").is(reviewId));
		query.addCriteria(Criteria.where("likes").in(userId));

		return mongoOperations.exists(query, Review.class);
	}

	@Override
	public boolean existsByDislike(String reviewId, String userId) {
		Query query = new Query();

		query.addCriteria(Criteria.where("id").is(reviewId));
		query.addCriteria(Criteria.where("dislikes").in(userId));

		return mongoOperations.exists(query, Review.class);
	}

}
