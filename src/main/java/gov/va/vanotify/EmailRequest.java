package gov.va.vanotify;

import org.json.JSONObject;

import java.util.Map;

/**
 * Represents email notification request
 * To create an instance use the static builder with fluent API
 * {@link EmailRequest.Builder}
 */
public class EmailRequest implements NotificationRequest {
    private final String templateId;
    private final String emailAddress;
    private final Map<String, ?> personalisation;
    private final String reference;
    private final String emailReplyToId;
    private final String billingCode;

    private EmailRequest(Builder builder) {
        this.templateId = builder.templateId;
        this.emailAddress = builder.emailAddress;
        this.personalisation = builder.personalisation;
        this.reference = builder.reference;
        this.emailReplyToId = builder.emailReplyToId;
        this.billingCode = builder.billingCode;

        if (this.templateId == null || this.templateId.isEmpty()) throw new IllegalStateException("Missing templateId");
        if (this.emailAddress == null || this.emailAddress.isEmpty()) throw new IllegalStateException("Missing emailAddress");
    }

    public String getTemplateId() {
        return templateId;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public Map<String, ?> getPersonalisation() {
        return personalisation;
    }

    public String getReference() {
        return reference;
    }

    public String getEmailReplyToId() {
        return emailReplyToId;
    }

    public String getBillingCode() {
        return billingCode;
    }

    @Override
    public JSONObject asJson() {
        JSONObject body = new JSONObject();

        body.put("template_id", templateId);

        if(emailAddress != null && !emailAddress.isEmpty()) {
            body.put("email_address", emailAddress);
        }

        if (personalisation != null && !personalisation.isEmpty()) {
            body.put("personalisation", personalisation);
        }

        if(reference != null && !reference.isEmpty()){
            body.put("reference", reference);
        }

        if(billingCode != null && !billingCode.isEmpty()){
            body.put("billing_code", billingCode);
        }

        if(emailReplyToId != null && !emailReplyToId.isEmpty())
        {
            body.put("email_reply_to_id", emailReplyToId);
        }
        return body;
    }


    /**
     * Fluent API Builder for EmailRequest
     */
    public static class Builder {
        private String templateId;
        private String emailAddress;
        private Map<String, ?> personalisation;
        private String reference;
        private String emailReplyToId;
        private String billingCode;


        /**
         * Sets <b>required</b> templateId.
         * @param templateId    The template id is visible on the template page in the application.
         * @return reference to itself (builder)
         */
        public Builder withTemplateId(String templateId) {
            this.templateId = templateId;
            return this;
        }

        /**
         * Sets <b>required</b> emailAddress.
         * @param emailAddress  The email address
         * @return reference to itself (builder)
         */
        public Builder withEmailAddress(String emailAddress) {
            this.emailAddress = emailAddress;
            return this;
        }

        /**
         * Sets <b>optional</b> personalisations.
         * @param personalisation   Map representing the placeholders for the template if any. For example, key=name value=Bob
         * @return reference to itself (builder)
         */
        public Builder withPersonalisation(Map<String, ?> personalisation) {
            this.personalisation = personalisation;
            return this;
        }

        /**
         * Sets <b>optional</b> reference
         * @param reference A reference specified by the service for the notification. Get all notifications can be filtered by this reference.
         *                  This reference can be unique or used used to refer to a batch of notifications.
         * @return reference to itself (builder)
         */
        public Builder withReference(String reference) {
            this.reference = reference;
            return this;
        }

        /**
         * Sets <b>optional</b> emailReplyToId.
         * Will use the default service email reply to address if not provided.
         * @param emailReplyToId    An optional identifier for a reply to email address for the notification, rather than use the service default.
         *                          Service emailReplyToIds can be accessed via the service settings / manage email reply to addresses page.
         * @return reference to itself (builder)
         */
        public Builder withEmailReplyToId(String emailReplyToId) {
            this.emailReplyToId = emailReplyToId;
            return this;
        }

        /**
         *
         * @param billingCode   A billing code specified by the service for the notification.
         *                      Used to group notifications for billing and reporting.
         * @return reference to itself (builder)
         */
        public Builder withBillingCode(String billingCode) {
            this.billingCode = billingCode;
            return this;
        }

        /**
         * Builds {@link EmailRequest}
         * @return <code>EmailRequest</code>
         * @throws IllegalStateException if any required fields are missing
         */
        public EmailRequest build() {
            return new EmailRequest(this);
        }
    }
}
