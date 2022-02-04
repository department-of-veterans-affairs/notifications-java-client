package gov.va.vanotify;

import java.util.UUID;

public class SendLetterResponse extends LetterResponse {
    private final TemplateDTO template;
    private final Content content;

    public SendLetterResponse(UUID notificationId, String reference, String postage, TemplateDTO template, Content content) {
        super(notificationId, reference, postage);
        this.template = template;
        this.content = content;
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

    @Override
    public String toString() {
        return "SendLetterResponse{" +
                "notificationId=" + getNotificationId() +
                ", reference=" + getReference() +
                ", templateId=" + getTemplateId() +
                ", templateVersion=" + getTemplateVersion() +
                ", templateUri='" + getTemplateUri() + '\'' +
                ", body='" + getBody() + '\'' +
                ", subject='" + getSubject() +
                '}';
    }

    private class Content {
        private final String body;
        private final String subject;

        public Content(String body, String subject) {
            this.body = body;
            this.subject = subject;
        }

        public String getBody() {
            return body;
        }

        public String getSubject() {
            return subject;
        }
    }
}
