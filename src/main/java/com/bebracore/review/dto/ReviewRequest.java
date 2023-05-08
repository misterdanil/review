package com.bebracore.review.dto;

import com.bebracore.cabinet.model.User;
import com.bebracore.productswatching.model.Product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ReviewRequest {
	@NotBlank(message = "Введите заголовок отзыва")
	private String title;
	@NotBlank(message = "Введите текст отзыва")
	private String text;
	@NotNull(message = "Поставьте оценку товару")
	private Integer rating;
	private Product product;
	private User user;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
