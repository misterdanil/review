package com.bebracore.review.dto.generator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bebracore.cabinet.dto.generator.UserDtoGenerator;
import com.bebracore.cabinet.service.UserService;
import com.bebracore.review.dto.ReviewDto;
import com.bebracore.review.model.Review;

@Component
public class ReviewDtoGenerator {
	@Autowired
	private UserService userService;

	public ReviewDto createDto(Review review) {
		ReviewDto dto = new ReviewDto();
		dto.setId(review.getId());
		dto.setTitle(review.getTitle());
		dto.setText(review.getText());
		dto.setAdvantages(review.getAdvantages());
		dto.setDisadvantages(review.getDisadvantages());
		dto.setCreatedOn(review.getCreatedOn());
		dto.setUserId(review.getUserId());
		dto.setUsername(userService.findById(review.getUserId()).getUsername());
		dto.setProductId(review.getProductId());
		dto.setLikes(review.getLikes());
		dto.setDislikes(review.getDislikes());
		dto.setRating(review.getRating());

		return dto;
	}

	public List<ReviewDto> createDtos(List<Review> reviews) {
		List<ReviewDto> dtos = new ArrayList<>();
		reviews.forEach(review -> {
			dtos.add(createDto(review));
		});
		return dtos;
	}
}
