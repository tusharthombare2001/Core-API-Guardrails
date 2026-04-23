package com.imotive.core_api_guardrails.services;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class NotificationScheduler {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Scheduled(fixedRate = 60000) 
    public void processNotifications() {

        Set<String> keys = redisTemplate.keys("user:*:pending_notifs");

        if (keys == null || keys.isEmpty()) return;

        for (String key : keys) {

            List<String> messages = redisTemplate.opsForList().range(key, 0, -1);

            if (messages == null || messages.isEmpty()) continue;

            int count = messages.size();

           
            String firstMessage = messages.get(0);

            System.out.println("Summarized Push Notification: " +
                    firstMessage + " and " + (count - 1) + " others");

           
            redisTemplate.delete(key);
        }
    }

}
