package gov.va.vanotify;

import org.json.JSONObject;

import java.util.Optional;
import java.util.UUID;

public class LetterResponse {
    private final UUID notificationId;
    private final String reference;
    private final String postage;
    private final JSONObject data;

    public LetterResponse(String response) {
        data = new JSONObject(response);
        notificationId = UUID.fromString(data.getString("id"));
        reference = data.isNull("reference") ? null : data.getString("reference");
        postage = data.isNull("postage") ? null : data.getString("postage");

    }

    public UUID getNotificationId() {
        return notificationId;
    }

    public Optional<String> getReference() {
        return Optional.ofNullable(reference);
    }

    public JSONObject getData() {
        return data;
    }

    public Optional<String> getPostage() {
        return Optional.ofNullable(postage);
    }

    @Override
    public String toString() {
        return "SendLetterResponse{" +
                "notificationId=" + notificationId +
                ", reference=" + reference +
                '}';
    }

    public String tryToGetString(JSONObject jsonObj, String key)
    {
        if (jsonObj.has(key))
        {
            if(jsonObj.opt(key).toString().equals("null"))
            {
                return null;
            }
            else
            {
                return jsonObj.opt(key).toString();
            }
        }

        return null;
    }
}
