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

    public ReceivedTextMessage(String json){
        JSONObject responseBodyAsJson = new JSONObject(json);
        build(responseBodyAsJson);
    }

    public ReceivedTextMessage(org.json.JSONObject data){
        build(data);
    }

    private void build(JSONObject data) {
        id = UUID.fromString(data.getString("id"));

        notifyNumber= data.getString("notify_number");
        userNumber = data.getString("user_number");
        serviceId = UUID.fromString(data.getString("service_id"));
        content = data.getString("content");
        createdAt = new DateTime(data.getString("created_at"));
    }


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
