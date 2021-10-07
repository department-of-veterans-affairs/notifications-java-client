package gov.va.vanotify;

import com.google.gson.annotations.SerializedName;

import java.util.Optional;
import java.util.UUID;

public class SendSmsResponse {
    @SerializedName("id")
    private final UUID notificationId;
    private final String reference;
    private final TemplateDTO template;
    private final Content content;
    private final String billingCode;

    public SendSmsResponse(UUID notificationId, String reference, TemplateDTO template, Content content, String billingCode) {
        this.notificationId = notificationId;
        this.reference = reference;
        this.template = template;
        this.content = content;
        this.billingCode = billingCode;
    }

    public UUID getNotificationId() {
        return notificationId;
    }

    public Optional<String> getReference() {
        return Optional.ofNullable(reference);
    }

    public UUID getTemplateId() {
        return template.getId();
    }

    public int getTemplateVersion() {
        return template.getVersion();
    }

    public String getTemplateUri() {
        return template.getUri();
    }

    public String getBody() {
        return content.getBody();
    }

    public Optional<String> getFromNumber() {
        return Optional.ofNullable(content.getFromNumber());
    }

    public Optional<String> getBillingCode() { return Optional.ofNullable(billingCode); }

    @Override
    public String toString() {
        return "SendSmsResponse{" +
                "notificationId=" + notificationId +
                ", reference=" + reference +
                ", templateId=" + getTemplateId() +
                ", templateVersion=" + getTemplateVersion() +
                ", templateUri='" + getTemplateUri() + '\'' +
                ", body='" + getBody() + '\'' +
                ", fromNumber=" + getFromNumber() +
                ", billingCode=" + billingCode +
                '}';
    }

    private class Content {
        private final String body;
        private final String fromNumber;

        public Content(String body, String fromNumber) {
            this.body = body;
            this.fromNumber = fromNumber;
        }

        public String getBody() {
            return body;
        }

        public String getFromNumber() {
            return fromNumber;
        }
    }
}

