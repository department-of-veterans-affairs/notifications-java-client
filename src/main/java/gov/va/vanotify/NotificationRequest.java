package gov.va.vanotify;

import org.json.JSONObject;

import java.util.Map;

public abstract class NotificationRequest {
    protected final String templateId;
    protected final String recipient;
    protected final Map<String, ?> personalisation;
    protected final String reference;
    protected final String billingCode;

    protected NotificationRequest(Builder builder) {
        this.templateId = builder.templateId;
        this.recipient = builder.recipient;
        this.personalisation = builder.personalisation;
        this.reference = builder.reference;
        this.billingCode = builder.billingCode;

        if (this.templateId == null || this.templateId.isEmpty()) throw new IllegalStateException("Missing templateId");
    }

    public String getTemplateId() {
        return templateId;
    }

    public Map<String, ?> getPersonalisation() {
        return personalisation;
    }

    public String getReference() {
        return reference;
    }

    public String getBillingCode() {
        return billingCode;
    }

    public abstract JSONObject asJson();

    public abstract static class Builder<T extends NotificationRequest, B extends Builder> {
        protected String templateId;
        protected String recipient;
        protected Map<String, ?> personalisation;
        protected String reference;
        protected String emailReplyToId;
        protected String billingCode;

        protected abstract B getInstance();

        /**
         * Sets <b>required</b> recipient.
         * @param recipient  The email address or phone number
         * @return reference to itself (builder)
         */
        protected B withRecipient(String recipient) {
            this.recipient = recipient;
            return this.getInstance();
        }

        /**
         * Sets <b>required</b> templateId.
         * @param templateId    The template id is visible on the template page in the application.
         * @return reference to itself (builder)
         */
        public B withTemplateId(String templateId) {
            this.templateId = templateId;
            return this.getInstance();
        }

        /**
         * Sets <b>optional</b> personalisations.
         * @param personalisation   Map representing the placeholders for the template if any. For example, key=name value=Bob
         * @return reference to itself (builder)
         */
        public B withPersonalisation(Map<String, ?> personalisation) {
            this.personalisation = personalisation;
            return this.getInstance();
        }

        /**
         * Sets <b>optional</b> reference
         * @param reference A reference specified by the service for the notification. Get all notifications can be filtered by this reference.
         *                  This reference can be unique or used used to refer to a batch of notifications.
         * @return reference to itself (builder)
         */
        public B withReference(String reference) {
            this.reference = reference;
            return this.getInstance();
        }

        /**
         * Sets <b>optional</b> emailReplyToId.
         * Will use the default service email reply to address if not provided.
         * @param emailReplyToId    An optional identifier for a reply to email address for the notification, rather than use the service default.
         *                          Service emailReplyToIds can be accessed via the service settings / manage email reply to addresses page.
         * @return reference to itself (builder)
         */
        public B withEmailReplyToId(String emailReplyToId) {
            this.emailReplyToId = emailReplyToId;
            return this.getInstance();
        }

        /**
         *
         * @param billingCode   A billing code specified by the service for the notification.
         *                      Used to group notifications for billing and reporting.
         * @return reference to itself (builder)
         */
        public B withBillingCode(String billingCode) {
            this.billingCode = billingCode;
            return this.getInstance();
        }

        /**
         * Abstract method for building a NotificationRequest
         * Use one of concrete implementations:
         * {@link SmsRequest.Builder}
         * {@link EmailRequest.Builder}
         *
         * @throws IllegalStateException if any required fields are missing
         */
        public abstract T build();
    }
}
