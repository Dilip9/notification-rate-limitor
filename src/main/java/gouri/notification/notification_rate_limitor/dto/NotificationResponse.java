package gouri.notification.notification_rate_limitor.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotificationResponse {
    private String status;
    private String message;
}
