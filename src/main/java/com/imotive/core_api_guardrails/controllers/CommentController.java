package com.imotive.core_api_guardrails.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.imotive.core_api_guardrails.entity.Comment;
import com.imotive.core_api_guardrails.services.CommentService;

@RestController
@RequestMapping("/posts")
public class CommentController {
	
	private CommentService commentService; 
	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}
	
	@PostMapping("/{postId}/comment")
	public ResponseEntity<Comment> addComment(@PathVariable Long postId, @RequestBody Comment comment){
		
		Comment saveComment = commentService.addComment(postId, comment);
		return ResponseEntity.ok(saveComment);
		
	}
}
