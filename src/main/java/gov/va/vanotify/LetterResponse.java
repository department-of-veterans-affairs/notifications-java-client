package gov.va.vanotify;

import com.google.gson.annotations.SerializedName;

import java.util.Optional;
import java.util.UUID;

public class LetterResponse {
    @SerializedName("id")
    private final UUID notificationId;
    private final String reference;
    private final String postage;

    public LetterResponse(UUID notificationId, String reference, String postage) {
        this.notificationId = notificationId;
        this.reference = reference;
        this.postage = postage;
    }

    public UUID getNotificationId() {
        return notificationId;
    }

    public Optional<String> getReference() {
        return Optional.ofNullable(reference);
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
}
