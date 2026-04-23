package com.imotive.core_api_guardrails.services;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class NotificationService {
	
	private StringRedisTemplate redisTemplate;
	public NotificationService (StringRedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
	
	 private static final long COOLDOWN_MINUTES = 15;

	    public void handleBotInteraction(Long userId, String message) {

	        String cooldownKey = "user:" + userId + ":notif_cooldown";
	        String pendingKey = "user:" + userId + ":pending_notifs";

	       
	        Boolean hasCooldown = redisTemplate.hasKey(cooldownKey);

	        if (Boolean.TRUE.equals(hasCooldown)) {
	          
	            redisTemplate.opsForList().rightPush(pendingKey, message);

	        } else {
	          
	            System.out.println("Push Notification Sent to User " + userId + ": " + message);

	           
	            redisTemplate.opsForValue().set(
	                    cooldownKey,
	                    "active",
	                    Duration.ofMinutes(COOLDOWN_MINUTES)
	            );
	        }
	    }

}
