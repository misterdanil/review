package com.bebracore.review.dto;

import java.util.Date;
import java.util.List;

public class ReviewDto {
	private String id;
	private String title;
	private String text;
	private String advantages;
	private String disadvantages;
	private Date createdOn;
	private String userId;
	private String username;
	private String productId;
	private Integer rating;
	private List<String> likes;
	private List<String> dislikes;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public String getAdvantages() {
		return advantages;
	}

	public void setAdvantages(String advantages) {
		this.advantages = advantages;
	}

	public String getDisadvantages() {
		return disadvantages;
	}

	public void setDisadvantages(String disadvantages) {
		this.disadvantages = disadvantages;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public List<String> getLikes() {
		return likes;
	}

	public void setLikes(List<String> likes) {
		this.likes = likes;
	}

	public List<String> getDislikes() {
		return dislikes;
	}

	public void setDislikes(List<String> dislikes) {
		this.dislikes = dislikes;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

}
