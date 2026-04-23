package com.imotive.core_api_guardrails.services;

import org.springframework.stereotype.Service;

import com.imotive.core_api_guardrails.entity.Comment;
import com.imotive.core_api_guardrails.entity.Post;
import com.imotive.core_api_guardrails.exception.BotCooldownException;
import com.imotive.core_api_guardrails.exception.BotLimitExceededException;
import com.imotive.core_api_guardrails.exception.InvalidPostException;
import com.imotive.core_api_guardrails.exception.PostNotFoundException;
import com.imotive.core_api_guardrails.repository.CommentRepository;
import com.imotive.core_api_guardrails.repository.PostRepository;

@Service
public class CommentService {
	private CommentRepository commentRepository;
	private PostRepository postRepository;
	private RedisService redisService;
	private NotificationService notificationService;
	
	public CommentService(CommentRepository commentRepository, PostRepository postRepository,RedisService redisService, NotificationService notificationService) {
		this.commentRepository = commentRepository;
		this.postRepository = postRepository;
		this.redisService = redisService;
		this.notificationService = notificationService;
	}
	
	public Comment addComment(Long postId, Comment comment) {
		Post post = postRepository.findById(postId)
				.orElseThrow(() ->  new PostNotFoundException("Post Not Found With id : " + postId));
		
		if(comment.getAuthorId() == null || comment.getAuthorId() <= 0) {
			throw new InvalidPostException("Author ID must be greater than 0");
		}
		if(comment.getContent() == null || comment.getContent().isEmpty()) {
			throw new InvalidPostException("Content cannot be empty");
		}
		
		if(comment.getDepthLevel() < 0) {
			throw new InvalidPostException("Depth level cannot be negative");
		}
		
		if ("BOT".equalsIgnoreCase(comment.getAuthorType())) {

		    Long botId = comment.getAuthorId();   
		    Long userId = post.getAuthorId();     

		    //we can disabled cooldown for testing.
		    if (redisService.isCooldownActive(botId, userId)) {
		        throw new BotCooldownException("Bot is in cooldown for this user");
		    }

		   
		    if (redisService.isBotLimitExceeded(postId)) {
		        throw new BotLimitExceededException("Bot limit exceeded for this post");
		    }

		
		    redisService.setCooldown(botId, userId);

		    
		    redisService.incrementBotCount(postId);
		}
		
		
		 redisService.incrementViralityScore(postId, 50);
		
		comment.setPost(post);
		Comment savedComment = commentRepository.save(comment);
		
		if ("BOT".equalsIgnoreCase(comment.getAuthorType()) &&
			    "USER".equalsIgnoreCase(post.getAuthorType())) {

			    Long userId = post.getAuthorId();

			    String message = "Bot " + comment.getAuthorId() + " replied to your post";

			    notificationService.handleBotInteraction(userId, message);
			}

			return savedComment;
		
	}
	
}
