package com.bebracore.review.service;

import java.util.List;

import com.bebracore.productswatching.service.error.RatedAlreadyException;
import com.bebracore.review.dto.ReviewRequest;
import com.bebracore.review.model.Review;
import com.bebracore.review.service.error.ReviewNotFoundException;

public interface ReviewService {
	Review save(ReviewRequest request) throws RatedAlreadyException;

	List<Review> getByProductId(String productId);

	List<Review> getByUserId(String userId);

	Review like(String reviewId) throws ReviewNotFoundException;

	Review dislike(String reviewId) throws ReviewNotFoundException;
}
