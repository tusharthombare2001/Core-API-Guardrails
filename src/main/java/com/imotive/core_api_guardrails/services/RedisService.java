package com.imotive.core_api_guardrails.services;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {
	  @Autowired
	    private RedisTemplate<String, Object> redisTemplate;
	   public void incrementViralityScore(Long postId, int score) {
	        String key = "post:" + postId + ":virality_score";
	        redisTemplate.opsForValue().increment(key, score);
	    }

	    public Object getViralityScore(Long postId) {
	        String key = "post:" + postId + ":virality_score";
	        return redisTemplate.opsForValue().get(key);
	    }
	    
	    public Long incrementBotCount(Long postId) {
	        String key = "post:" + postId + ":bot_count";
	        return redisTemplate.opsForValue().increment(key);
	    }
	    
	    public boolean isBotLimitExceeded(Long postId) {
	        String key = "post:" + postId + ":bot_count";
	        Object count = redisTemplate.opsForValue().get(key);

	        if (count == null) return false;

	        return Long.parseLong(count.toString()) >= 100;
	    }
	    
	    public boolean isCooldownActive(Long botId, Long userId) {
	        String key = "cooldown:bot_" + botId + ":human_" + userId;
	        return redisTemplate.hasKey(key);
	    }
	    
	    public void setCooldown(Long botId, Long userId) {
	        String key = "cooldown:bot_" + botId + ":human_" + userId;
	        redisTemplate.opsForValue().set(key, "1", 10, TimeUnit.MINUTES);
	    }

}
