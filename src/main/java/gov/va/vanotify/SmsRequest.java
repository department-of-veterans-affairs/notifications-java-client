package gov.va.vanotify;

import org.json.JSONObject;

import java.util.Map;

/**
 * Represents SMS notification request
 * To create an instance use the static builder with fluent API
 * {@link SmsRequest.Builder}
 */
public class SmsRequest implements NotificationRequest {
    private final String templateId;
    private final String phoneNumber;
    private final Map<String, ?> personalisation;
    private final String reference;
    private final String smsSenderId;
    private final String billingCode;

    private SmsRequest(Builder builder) {
        this.templateId = builder.templateId;
        this.phoneNumber = builder.phoneNumber;
        this.personalisation = builder.personalisation;
        this.reference = builder.reference;
        this.smsSenderId = builder.smsSenderId;
        this.billingCode = builder.billingCode;

        if (this.templateId == null || this.templateId.isEmpty()) throw new IllegalStateException("Missing templateId");
        if (this.phoneNumber == null || this.phoneNumber.isEmpty()) throw new IllegalStateException("Missing phoneNumber");
    }

    public String getTemplateId() {
        return templateId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Map<String, ?> getPersonalisation() {
        return personalisation;
    }

    public String getReference() {
        return reference;
    }

    public String getSmsSenderId() {
        return smsSenderId;
    }

    public String getBillingCode() {
        return billingCode;
    }

    @Override
    public JSONObject asJson() {
        JSONObject body = new JSONObject();

        body.put("template_id", templateId);

        if(phoneNumber != null && !phoneNumber.isEmpty()) {
            body.put("phone_number", phoneNumber);
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

        if(smsSenderId != null && !smsSenderId.isEmpty())
        {
            body.put("sms_sender_id", smsSenderId);
        }
        return body;
    }

    /**
     * Fluent API Builder for SmsRequest
     */
    public static class Builder {
        private String templateId;
        private String phoneNumber;
        private Map<String, ?> personalisation;
        private String reference;
        private String smsSenderId;
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
         * Sets <b>required</b> phoneNumber.
         * @param phoneNumber  The mobile phone number
         * @return reference to itself (builder)
         */
        public Builder withPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
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
         * Builds {@link SmsRequest}
         * @return <code>SmsRequest</code>
         * @throws IllegalStateException if any required fields are missing
         */
        public SmsRequest build() {
            return new SmsRequest(this);
        }
    }
}
