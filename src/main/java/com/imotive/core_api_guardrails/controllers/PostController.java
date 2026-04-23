package com.imotive.core_api_guardrails.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.imotive.core_api_guardrails.entity.Post;
import com.imotive.core_api_guardrails.services.PostService;

@RestController
@RequestMapping("/posts")
public class PostController {
	private PostService postService;
	
	public PostController(PostService postService) {
		this.postService = postService;
	}
	
	@PostMapping
	public ResponseEntity<Post> createPost(@RequestBody Post post){
		Post savePost = postService.createPost(post);
		
		return ResponseEntity.status(201).body(savePost);
	}
	@GetMapping
	public ResponseEntity<List<Post>> getAllPosts(){
		List<Post> posts = postService.getAllPosts();
		return ResponseEntity.ok(posts);
	}
	@PostMapping("/{postId}/like")
	public ResponseEntity<Post> likePost(@PathVariable Long postId){
		return ResponseEntity.ok(postService.likePost(postId));
	}
	

}
