# Core-API-Guardrails
Used Redis atomic operations (INCR, TTL) to ensure thread-safe guardrails and prevent race conditions.
 
 Tech Stack
- Java 17  
- Spring Boot 3.x  
- PostgreSQL  
- Redis (Spring Data Redis)  
- Docker

- ## Features
- - Create Post
  - get all post
- Add Comment  
- Like Post

- Redis Virality Engine
- Real-time virality score tracking  
- Based on interactions:
  - Bot Reply → +1  
  - Human Like → +20  
  - Human Comment → +50
 
  - Bot Guardrails (Concurrency Control)
Implemented using Redis atomic operations:

- **Horizontal Cap**  
  - Max 100 bot replies per post  

- **Cooldown Cap**  
  - A bot cannot interact with the same user within 10 minutes  

- **Vertical Cap**  
  - Comment depth limited to 20 levels
 
- Notification Engine
- Instant notification for first bot interaction  
- Batched notifications stored in Redis  
- Prevents notification spam

- Scheduler (CRON Sweeper)
- Runs every 5 minutes  
- Aggregates pending notifications  
- Sends summarized output  
- Clears Redis queue

How to Run the Project

### Step 1: Start Services (PostgreSQL + Redis)

```bash
docker-compose up
