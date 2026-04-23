package com.imotive.core_api_guardrails.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
@Entity
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Post {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	private Long authorId;
	private String authorType;   //extra fild for who is there bot or user
	private String content;
	private LocalDateTime createdAt;
	
	@Column(nullable = false)
	private int likeCount = 0;
	
	
	 public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public Long getAuthorId() {
		return authorId;
	}


	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}

	public String getAuthorType() {
		return authorType;
	}




	public void setAuthorType(String authorType) {
		this.authorType = authorType;
	}

	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	


	public int getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}

	@PrePersist
	    public void prePersist() {
	        this.createdAt = LocalDateTime.now();
	    }

}
