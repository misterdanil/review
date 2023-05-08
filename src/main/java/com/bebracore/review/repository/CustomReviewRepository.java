package com.bebracore.review.repository;

public interface CustomReviewRepository {
	boolean existsByLike(String reviewId, String userId);

	boolean existsByDislike(String reviewId, String userId);
}
