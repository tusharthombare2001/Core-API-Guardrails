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
Techniques Used

1. Atomic Counters (INCR)
   - Redis `INCR` operation was used to maintain counters like:
     - `post:{id}:bot_count`
     - `post:{id}:virality_score`
   - Since `INCR` is atomic, it guarantees that increments are thread-safe and no race conditions occur.

2. Horizontal Cap Enforcement
   - Bot comments are allowed only if:
     - `post:{id}:bot_count < 100`
   - The check and increment are handled using Redis, ensuring no more than 100 bot comments are accepted even under concurrent requests.

3. Cooldown Mechanism (TTL-based Locks)
   - A Redis key:
     ```
     cooldown:bot_{id}:human_{id}
     ```
   - Is set with a TTL of 10 minutes.
   - If the key exists, the request is rejected.
   - This ensures rate limiting without manual synchronization.

4. Stateless Design
   - No in-memory storage (like HashMap) was used.
   - All state is stored in Redis, ensuring consistency across multiple instances.

###  Result

- Prevented race conditions during concurrent bot interactions  
- Guaranteed strict enforcement of limits (e.g., max 100 bot comments)  
- Ensured system remains scalable and stateless  

This approach ensures strong consistency and reliability using Redis as a distributed lock and state manager.
 
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
