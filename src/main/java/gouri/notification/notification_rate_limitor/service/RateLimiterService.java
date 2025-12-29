package gouri.notification.notification_rate_limitor.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RateLimiterService {

    @Value("${rate.limiter.max.requests}")
    private int maxRequests;

    @Value("${rate.limiter.window.seconds}")
    private int windowSeconds;

    private final StringRedisTemplate redisTemplate;

    private final DefaultRedisScript<Long> rateLimiterScript;

    public RateLimiterService(StringRedisTemplate redisTemplate, DefaultRedisScript<Long> rateLimiterScript) {
        this.redisTemplate = redisTemplate;
        this.rateLimiterScript = rateLimiterScript;
    }

    public boolean allow(String key){
        Long result = redisTemplate.execute(
                rateLimiterScript,
                List.of(key),
                String.valueOf(maxRequests),
                String.valueOf(windowSeconds)
        );
        return result != null && result == 1;
    }
}
