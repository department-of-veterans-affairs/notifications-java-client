package gov.va.vanotify;

import org.json.JSONObject;

import java.util.Map;

/**
 * Represents email notification request
 * To create an instance use the static builder with fluent API
 * {@link EmailRequest.Builder}
 * {@link NotificationRequest.Builder}
 */
public class EmailRequest extends NotificationRequest {
    private final String emailReplyToId;

    private EmailRequest(Builder builder) {
        super(builder);
        this.emailReplyToId = builder.emailReplyToId;
        if (this.recipient == null || this.recipient.isEmpty()) throw new IllegalStateException("Missing emailAddress");
    }

    public String getEmailAddress() {
        return this.recipient;
    }

    public String getEmailReplyToId() {
        return emailReplyToId;
    }

    @Override
    public JSONObject asJson() {
        JSONObject body = new JSONObject();

        body.put("template_id", templateId);

        if(recipient != null && !recipient.isEmpty()) {
            body.put("email_address", recipient);
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
     * Please see {@link NotificationRequest.Builder} for builder methods shared between all notification requests
     */
    public static class Builder extends NotificationRequest.Builder<EmailRequest, Builder> {
        private String emailReplyToId;

        @Override
        protected Builder getInstance() {
            return this;
        }

        /**
         * Sets <b>required</b> emailAddress.
         * @param emailAddress  The email address
         * @return reference to itself (builder)
         */
        public Builder withEmailAddress(String emailAddress) {
            this.withRecipient(emailAddress);
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
         * Builds {@link EmailRequest}
         * @return <code>EmailRequest</code>
         * @throws IllegalStateException if any required fields are missing
         */
        @Override
        public EmailRequest build() {
            return new EmailRequest(this);
        }
    }
}
