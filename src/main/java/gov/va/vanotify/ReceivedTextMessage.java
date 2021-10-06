package gov.va.vanotify;

import org.joda.time.DateTime;
import org.json.JSONObject;

import java.util.UUID;

public class ReceivedTextMessage {
    private UUID id;
    private String notifyNumber;
    private String userNumber;
    private UUID serviceId;
    private String content;
    private DateTime createdAt;

    public UUID getId() {
        return id;
    }

    public String getNotifyNumber() {
        return notifyNumber;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public UUID getServiceId() {
        return serviceId;
    }

    public String getContent() {
        return content;
    }

    public DateTime getCreatedAt() {
        return createdAt;
    }
}
