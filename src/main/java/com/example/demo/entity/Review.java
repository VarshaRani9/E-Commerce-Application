package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Review {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id") 
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int rating;
    private String comment;
    
	public Review(Long id, User user, Product product, int rating, String comment) {
		this.id = id;
		this.user = user;
		this.product = product;
		this.rating = rating;
		this.comment = comment;
	}
	public Review() {
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	@Override
	public String toString() {
		return "Review [id=" + id + ", user=" + user + ", product=" + product + ", rating=" + rating + ", comment="
				+ comment + "]";
	}
    
}
