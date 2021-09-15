package gov.va.vanotify;

import org.json.JSONObject;

import java.util.Optional;
import java.util.UUID;

public class SendSmsResponse {
    private final UUID notificationId;
    private final String reference;
    private final UUID templateId;
    private final int templateVersion;
    private final String templateUri;
    private final String body;
    private final String fromNumber;
    private final String billingCode;


    public SendSmsResponse(String response) {
        JSONObject data = new JSONObject(response);
        notificationId = UUID.fromString(data.getString("id"));
        reference = data.isNull("reference") ? null : data.getString("reference");
        billingCode = data.isNull("billing_code") ? null : data.getString("billing_code");
        JSONObject content = data.getJSONObject("content");
        body = content.getString("body");
        fromNumber = content.isNull("from_number") ? null : content.getString("from_number");
        JSONObject template = data.getJSONObject("template");
        templateId = UUID.fromString(template.getString("id"));
        templateVersion = template.getInt("version");
        templateUri = template.getString("uri");
    }

    public UUID getNotificationId() {
        return notificationId;
    }

    public Optional<String> getReference() {
        return Optional.ofNullable(reference);
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

    public Optional<String> getFromNumber() {
        return Optional.ofNullable(fromNumber);
    }

    public Optional<String> getBillingCode() { return Optional.ofNullable(billingCode); }

    @Override
    public String toString() {
        return "SendSmsResponse{" +
                "notificationId=" + notificationId +
                ", reference=" + reference +
                ", templateId=" + templateId +
                ", templateVersion=" + templateVersion +
                ", templateUri='" + templateUri + '\'' +
                ", body='" + body + '\'' +
                ", fromNumber=" + fromNumber +
                ", billingCode=" + billingCode +
                '}';
    }
}

