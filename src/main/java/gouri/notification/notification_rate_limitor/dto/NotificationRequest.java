package gouri.notification.notification_rate_limitor.dto;

import gouri.notification.notification_rate_limitor.enums.ChannelType;
import gouri.notification.notification_rate_limitor.enums.MessageType;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class NotificationRequest {
    private String clientId;
    private String userId;
    private ChannelType channelType;
    private String recipient;

    @NotBlank
    private String content;
    private MessageType messageType;
}
