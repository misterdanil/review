package com.bebracore.review.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bebracore.cabinet.model.User;
import com.bebracore.cabinet.service.UserService;
import com.bebracore.productswatching.model.Product;
import com.bebracore.productswatching.service.ProductService;
import com.bebracore.productswatching.service.error.RatedAlreadyException;
import com.bebracore.review.dto.ReviewDto;
import com.bebracore.review.dto.ReviewRequest;
import com.bebracore.review.dto.generator.ReviewDtoGenerator;
import com.bebracore.review.model.Review;
import com.bebracore.review.service.ReviewService;
import com.bebracore.review.service.error.ReviewNotFoundException;
import com.bebracore.webconfig.controller.AbstractController;
import com.bebracore.webconfig.dto.ValidatedResponse;

@Controller
public class ReviewController extends AbstractController {
	@Autowired
	private ReviewService reviewService;
	@Autowired
	private ProductService productService;
	@Autowired
	private UserService userService;
	@Autowired
	private ReviewDtoGenerator dtoGenerator;

	private String hostLink = "http://localhost:8080";

	@PostMapping("/product/smartphone/{id}/review")
	@ResponseBody
	public ResponseEntity<ValidatedResponse> createReview(@PathVariable String id,
			@RequestBody @Validated ReviewRequest request, BindingResult result) {
		if (result.hasErrors()) {
			return ResponseEntity.badRequest().body(createErrorValidationResponse(result));
		}

		Product product = productService.getProductById(id);
		if (product == null) {
			result.rejectValue("product", "product.notFound", "Couldn't find a product with id " + id);
			return ResponseEntity.badRequest().body(createErrorValidationResponse(result));
		}
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findById(authentication.getName());
		if (user == null) {
			result.rejectValue("user", "user.notFound", "You aren't authenticated");
			return ResponseEntity.status(HttpStatusCode.valueOf(403)).body(createErrorValidationResponse(result));
		}

		request.setProduct(product);
		request.setUser(user);

		Review review;
		try {
			review = reviewService.save(request);
		} catch (RatedAlreadyException e1) {
			return ResponseEntity.badRequest().body(createErrorValidationResponse(result));
		}

		try {
			return ResponseEntity.created(new URI(hostLink + "/product/smartphone/" + id + "/review/" + review.getId()))
					.build();
		} catch (URISyntaxException e) {
			return ResponseEntity.status(500).build();
		}
	}

	@GetMapping("/product/smartphone/{id}/reviews")
	@ResponseBody
	public ResponseEntity<List<ReviewDto>> getReviews(@PathVariable String id) {
		List<Review> reviews = reviewService.getByProductId(id);

		return ResponseEntity.ok(dtoGenerator.createDtos(reviews));
	}

	@PostMapping("/product/smartphone/*/review/{id}/like")
	public ResponseEntity<ValidatedResponse> like(@PathVariable String id) {
		try {
			reviewService.like(id);
		} catch (ReviewNotFoundException e) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PostMapping("/product/smartphone/*/review/{id}/dislike")
	public ResponseEntity<ValidatedResponse> dislike(@PathVariable String id) {
		try {
			reviewService.dislike(id);
		} catch (ReviewNotFoundException e) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@GetMapping("/user/reviews")
	public ResponseEntity<List<ReviewDto>> getReviews() {
		String userId = SecurityContextHolder.getContext().getAuthentication().getName();

		List<Review> reviews = reviewService.getByUserId(userId);

		List<ReviewDto> dtos = dtoGenerator.createDtos(reviews);

		return ResponseEntity.ok(dtos);
	}
}
