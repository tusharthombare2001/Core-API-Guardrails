package com.imotive.core_api_guardrails.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imotive.core_api_guardrails.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>{

}
