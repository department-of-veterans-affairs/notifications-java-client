package gov.va.vanotify;

import com.google.gson.annotations.SerializedName;
import org.joda.time.DateTime;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Notification {
    private UUID id;
    private String reference;
    private String emailAddress;
    private String phoneNumber;
    @SerializedName("line_1")
    private String line1;
    @SerializedName("line_2")
    private String line2;
    @SerializedName("line_3")
    private String line3;
    @SerializedName("line_4")
    private String line4;
    @SerializedName("line_5")
    private String line5;
    @SerializedName("line_6")
    private String line6;
    private String postcode;
    private String postage;
    @SerializedName("type")
    private String notificationType;
    private String providerReference;
    private String status;
    private TemplateDTO template;
    private String body;
    private String subject;
    private DateTime createdAt;
    private String scheduledFor;
    private DateTime sentAt;
    public String sentBy;
    private DateTime completedAt;
    private DateTime estimatedDelivery;
    private String createdByName;
    private String billingCode;
    private List<Identifier> recipientIdentifiers;

    public UUID getId() {
        return id;
    }

    public Optional<String> getReference() {
        return Optional.ofNullable(reference);
    }

    public Optional<String> getEmailAddress() {
        return Optional.ofNullable(emailAddress);
    }

    public Optional<String> getPhoneNumber() {
        return Optional.ofNullable(phoneNumber);
    }

    public Optional<String> getLine1() {
        return Optional.ofNullable(line1);
    }

    public Optional<String> getLine2() {
        return Optional.ofNullable(line2);
    }

    public Optional<String> getLine3() {
        return Optional.ofNullable(line3);
    }

    public Optional<String> getLine4() {
        return Optional.ofNullable(line4);
    }

    public Optional<String> getLine5() {
        return Optional.ofNullable(line5);
    }

    public Optional<String> getLine6() {
        return Optional.ofNullable(line6);
    }

    public Optional<String> getPostcode() {
        return Optional.ofNullable(postcode);
    }
    public Optional<String> getPostage() {
        return Optional.ofNullable(postage);
    }

    public Optional<String> getProviderReference() {
        return Optional.ofNullable(providerReference);
    }

    public String getNotificationType() {
        return notificationType;
    }

    public String getStatus() {
        return status;
    }

    public UUID getTemplateId() {
        return Optional.ofNullable(this.template).map(TemplateDTO::getId).orElse(null);
    }

    public int getTemplateVersion() {
        return Optional.ofNullable(this.template).map(TemplateDTO::getVersion).orElse(null);
    }

    public String getTemplateUri(){
        return Optional.ofNullable(this.template).map(TemplateDTO::getUri).orElse(null);
    }

    public String getBody() {
        return body;
    }

    public Optional<String> getSubject() {
        return Optional.ofNullable(subject);
    }

    public DateTime getCreatedAt() {
        return createdAt;
    }

    public Optional<String> getScheduledFor() {
        return Optional.ofNullable(scheduledFor);
    }

    public Optional<DateTime> getSentAt() {
        return Optional.ofNullable(sentAt);
    }

    public Optional<String> getSentBy() {
        return Optional.ofNullable(sentBy);
    }

    public Optional<DateTime> getCompletedAt() {
        return Optional.ofNullable(completedAt);
    }

    public Optional<String> getCreatedByName() {
        return Optional.ofNullable(createdByName);
    }

    public Optional<String> getBillingCode() { return Optional.ofNullable(billingCode); }

    public List<Identifier> getRecipientIdentifiers() { return recipientIdentifiers; }

    /**
     * estimatedDelivery is only present on letters
     */
    public Optional<DateTime> getEstimatedDelivery() {
        return Optional.ofNullable(estimatedDelivery);
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", reference='" + reference + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", line1='" + line1 + '\'' +
                ", line2='" + line2 + '\'' +
                ", line3='" + line3 + '\'' +
                ", line4='" + line4 + '\'' +
                ", line5='" + line5 + '\'' +
                ", line6='" + line6 + '\'' +
                ", postcode='" + postcode + '\'' +
                ", provider_reference ='" + providerReference + '\'' +
                ", notificationType='" + notificationType + '\'' +
                ", status='" + status + '\'' +
                ", templateId=" + this.getTemplateId() +
                ", templateVersion=" + this.getTemplateVersion() +
                ", templateUri='" + this.getTemplateUri() + '\'' +
                ", body='" + body + '\'' +
                ", subject='" + subject + '\'' +
                ", createdAt=" + createdAt +
                ", sentAt=" + sentAt +
                ", completedAt=" + completedAt +
                ", estimatedDelivery=" + estimatedDelivery +
                ", createdByName=" + createdByName +
                ", billingCode=" + billingCode +
                ", recipientIdentifier=" + recipientIdentifiers +
                '}';
    }

}
