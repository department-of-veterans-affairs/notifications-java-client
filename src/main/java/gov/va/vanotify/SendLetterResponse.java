package gov.va.vanotify;

import org.json.JSONObject;

import java.util.UUID;

public class SendLetterResponse extends LetterResponse {
    private final UUID templateId;
    private final int templateVersion;
    private final String templateUri;
    private final String body;
    private final String subject;

    public SendLetterResponse(String response) {
        super(response);
        JSONObject content = getData().getJSONObject("content");
        body = tryToGetString(content, "body");
        subject = tryToGetString(content, "subject");
        JSONObject template = getData().getJSONObject("template");
        templateId = UUID.fromString(template.getString("id"));
        templateVersion = template.getInt("version");
        templateUri = template.getString("uri");
    }

    public UUID getTemplateId() {
        return templateId;
    }

    public int getTemplateVersion() {
        return templateVersion;
    }

    public String getTemplateUri() {
        return templateUri;
    }

    public String getBody() {
        return body;
    }

    public String getSubject() {
        return subject;
    }

    @Override
    public String toString() {
        return "SendLetterResponse{" +
                "notificationId=" + getNotificationId() +
                ", reference=" + getReference() +
                ", templateId=" + templateId +
                ", templateVersion=" + templateVersion +
                ", templateUri='" + templateUri + '\'' +
                ", body='" + body + '\'' +
                ", subject='" + subject +
                '}';
    }
}
