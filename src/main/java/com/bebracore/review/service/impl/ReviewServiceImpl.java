package com.bebracore.review.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.bebracore.productswatching.model.Product;
import com.bebracore.productswatching.service.ProductService;
import com.bebracore.productswatching.service.error.RatedAlreadyException;
import com.bebracore.review.dto.ReviewRequest;
import com.bebracore.review.model.Review;
import com.bebracore.review.repository.ReviewRepository;
import com.bebracore.review.service.ReviewService;
import com.bebracore.review.service.error.ReviewNotFoundException;

@Service
public class ReviewServiceImpl implements ReviewService {
	@Autowired
	private ReviewRepository reviewRepository;
	@Autowired
	private ProductService productService;

	@Override
	public Review save(ReviewRequest request) throws RatedAlreadyException {
		Review review = new Review();
		review.setTitle(request.getTitle());
		review.setText(request.getText());
		review.setCreatedOn(new Date());
		review.setProductId(request.getProduct().getId());
		review.setUserId(request.getUser().getId());
		review.setRating(request.getRating());

		Product product = request.getProduct();

		if (product.containsRating(request.getUser().getId())) {
			review = reviewRepository.findByUserIdAndProductId(request.getUser().getId(), product.getId());
			review.setTitle(request.getTitle());
			review.setText(request.getText());
//			throw new RatedAlreadyException(
//					"Exception occurred while raing product with id: " + request.getProduct().getId()
//							+ ". The product has already rated by user " + request.getUser().getId());
		}

		review = reviewRepository.save(review);
		product.getReviewIds().add(review.getId());

		product.getRatings().put(request.getUser().getId(), request.getRating());

		productService.save(request.getProduct());

		return review;
	}

	@Override
	public List<Review> getByProductId(String productId) {
		return reviewRepository.findByProductId(productId);
	}

	@Override
	public List<Review> getByUserId(String userId) {
		return reviewRepository.findByUserId(userId);
	}

	@Override
	public Review getReview(String productId, String userId) {
		return reviewRepository.findByUserIdAndProductId(userId, productId);
	}

	@Override
	public Review like(String reviewId) throws ReviewNotFoundException {
		Optional<Review> opt = reviewRepository.findById(reviewId);
		if (opt.isEmpty()) {
			throw new ReviewNotFoundException("Отзыва с таким id не существует");
		}

		Review review = opt.get();

		String userId = SecurityContextHolder.getContext().getAuthentication().getName();

		if (reviewRepository.existsByLike(reviewId, userId)) {
			review.getLikes().remove(userId);
		} else {
			if (reviewRepository.existsByDislike(reviewId, userId)) {
				review.getDislikes().remove(userId);
			}
			review.getLikes().add(userId);
		}
		return reviewRepository.save(review);
	}

	@Override
	public Review dislike(String reviewId) throws ReviewNotFoundException {
		Optional<Review> opt = reviewRepository.findById(reviewId);
		if (opt.isEmpty()) {
			throw new ReviewNotFoundException("Отзыва с таким id не существует");
		}

		Review review = opt.get();

		String userId = SecurityContextHolder.getContext().getAuthentication().getName();

		if (reviewRepository.existsByDislike(reviewId, userId)) {
			review.getDislikes().remove(userId);
		} else {
			if (reviewRepository.existsByLike(reviewId, userId)) {
				review.getLikes().remove(userId);
			}
			review.getDislikes().add(userId);
		}
		return reviewRepository.save(review);
	}
}
