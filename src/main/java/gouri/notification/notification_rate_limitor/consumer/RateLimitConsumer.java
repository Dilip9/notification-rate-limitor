package gouri.notification.notification_rate_limitor.consumer;

import gouri.notification.notification_rate_limitor.dto.NotificationRequest;
import gouri.notification.notification_rate_limitor.service.RateLimiterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class RateLimitConsumer {

    private static final Logger logger = LoggerFactory.getLogger(RateLimitConsumer.class);

   private final RateLimiterService rateLimiterService;
   private final KafkaTemplate<String, Object> kafkaTemplate;

    public RateLimitConsumer(RateLimiterService rateLimiterService, KafkaTemplate<String, Object> kafkaTemplate) {
         this.rateLimiterService = rateLimiterService;
         this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = { "notification_priority_high",
                              "notification_priority_medium",
                              "notification_priority_low" })
    public void consume(NotificationRequest request) {

        String key = "r1:" + request.getRecipient() +":" +request.getChannelType();
        if(rateLimiterService.allow(key)) {
            kafkaTemplate.send("notification_allowed", request);
            logger.info("Notification allowed for {} via {}", request.getRecipient(), request.getChannelType());
        } else {
            logger.warn("Rate limit exceeded for {} via {}. Retrying...", request.getRecipient(), request.getChannelType());
            kafkaTemplate.send("notification_throttled", request);
        }

    }



}
