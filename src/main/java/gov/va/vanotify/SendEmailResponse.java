package gov.va.vanotify;

import com.google.gson.annotations.SerializedName;

import java.util.Optional;
import java.util.UUID;

public class SendEmailResponse {
    @SerializedName("id")
    private final UUID notificationId;
    private final String reference;
    private final TemplateDTO template;
    private final Content content;
    private final String billingCode;

    public SendEmailResponse(UUID notificationId, String reference, TemplateDTO template, Content content, String billingCode) {
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

    public String getSubject() {
        return content.getSubject();
    }

    public Optional<String> getFromEmail() {
        return Optional.ofNullable(content.fromEmail);
    }

    public Optional<String> getBillingCode() {
        return Optional.ofNullable(billingCode);
    }

    @Override
    public String toString() {
        return "SendEmailResponse{" +
                "notificationId=" + notificationId +
                ", reference=" + reference +
                ", templateId=" + getTemplateId() +
                ", templateVersion=" + getTemplateVersion() +
                ", templateUri='" + getTemplateUri() + '\'' +
                ", body='" + getBody() + '\'' +
                ", subject='" + getSubject() + '\'' +
                ", fromEmail=" + getFromEmail() +
                ", billingCode=" + billingCode +
                '}';
    }

    private class Content {
        private final String body;
        private final String subject;
        private final String fromEmail;

        public Content(String body, String subject, String fromEmail) {
            this.body = body;
            this.subject = subject;
            this.fromEmail = fromEmail;
        }

        public String getBody() {
            return body;
        }

        public String getSubject() {
            return subject;
        }

        public String getFromEmail() {
            return fromEmail;
        }
    }


}
