package com.imotive.core_api_guardrails.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.imotive.core_api_guardrails.entity.Post;
import com.imotive.core_api_guardrails.exception.InvalidPostException;
import com.imotive.core_api_guardrails.exception.PostNotFoundException;
import com.imotive.core_api_guardrails.repository.PostRepository;

@Service
public class PostService {
	private PostRepository postRepository;
	private RedisService redisService;
	
	public PostService(PostRepository postRepository,  RedisService redisService) {
		this.postRepository = postRepository;
		this.redisService = redisService;
	}
	
	public Post createPost(Post post) {
		if(post.getAuthorId() == null || post.getAuthorId() <= 0) {
			throw new InvalidPostException("Author ID can not be null");
		}
		if(post.getContent() == null || post.getContent().isEmpty()) {
			throw new InvalidPostException("Content cannot be empty");
		}
		return postRepository.save(post);
	}
	
	public List<Post> getAllPosts(){
		List<Post> posts = postRepository.findAll();
		if(posts.isEmpty()) {
		    throw new InvalidPostException("No posts available");
		}
		return posts;
		
	}
	
	public Post likePost(Long postId) {
		Post likePost = postRepository.findById(postId)
				.orElseThrow(() -> new PostNotFoundException("Post Not Found With Id : " + postId));
		likePost.setLikeCount(likePost.getLikeCount() + 1);
		
		redisService.incrementViralityScore(postId, 20);

		    return postRepository.save(likePost);
	}
}
