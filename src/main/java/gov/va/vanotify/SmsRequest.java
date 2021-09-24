package gov.va.vanotify;

import org.json.JSONObject;

/**
 * Represents SMS notification request
 * To create an instance use the static builder with fluent API
 * {@link SmsRequest.Builder}
 * {@link NotificationRequest.Builder}
 */
public class SmsRequest extends NotificationRequest {
    private final String smsSenderId;

    private SmsRequest(Builder builder) {
        super(builder);
        this.smsSenderId = builder.smsSenderId;
        if (this.missingRecipient() && this.recipientIdentifier == null) throw new IllegalStateException("Missing at least one of phoneNumber and recipientIdentifier");
    }

    public String getPhoneNumber() {
        return recipient;
    }

    public String getSmsSenderId() {
        return smsSenderId;
    }

    @Override
    public JSONObject asJson() {
        JSONObject body = super.asJson();

        if(recipient != null && !recipient.isEmpty()) {
            body.put("phone_number", recipient);
        }

        if(smsSenderId != null && !smsSenderId.isEmpty())
        {
            body.put("sms_sender_id", smsSenderId);
        }
        return body;
    }

    /**
     * Fluent API Builder for SmsRequest
     * Please see {@link NotificationRequest.Builder} for builder methods shared between all notification requests
     */
    public static class Builder extends NotificationRequest.Builder<SmsRequest, Builder>{
        private String smsSenderId;

        @Override
        protected Builder getInstance() {
            return this;
        }

        /**
         * Sets <b>required</b> phoneNumber.
         * @param phoneNumber  The mobile phone number
         * @return reference to itself (builder)
         */
        public Builder withPhoneNumber(String phoneNumber) {
            this.withRecipient(phoneNumber);
            return this;
        }

        /**
         * Sets <b>optional</b> smsSenderId.
         * Will use the default service sms sender if not provided.
         * @param smsSenderId    An optional identifier for the text message sender of the notification, rather than use the service default.
         *                       Service smsSenderIds can be accessed via the service settings / manage text message senders page.
         * @return reference to itself (builder)
         */
        public Builder withSmsSenderId(String smsSenderId) {
            this.smsSenderId = smsSenderId;
            return this;
        }

        /**
         * Builds {@link SmsRequest}
         * @return <code>SmsRequest</code>
         * @throws IllegalStateException if any required fields are missing
         */
        @Override
        public SmsRequest build() {
            return new SmsRequest(this);
        }
    }
}
