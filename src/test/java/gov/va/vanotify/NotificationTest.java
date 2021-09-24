package gov.va.vanotify;

import org.joda.time.DateTime;
import org.jose4j.json.internal.json_simple.JSONArray;
import org.jose4j.json.internal.json_simple.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class NotificationTest {

    @Test
    public void testEmailNotification_canCreateObjectFromJson() {
        JSONObject content = new JSONObject();
        String id = UUID.randomUUID().toString();
        content.put("id", id);
        content.put("reference", "client_reference");
        content.put("email_address", "some@address.com");
        content.put("phone_number", null);
        content.put("line_1", null);
        content.put("line_2", null);
        content.put("line_3", null);
        content.put("line_4", null);
        content.put("line_5", null);
        content.put("line_6", null);
        content.put("postcode", null);
        content.put("postage", null);
        content.put("type", "email");
        content.put("status", "delivered");
        JSONObject template = new JSONObject();
        String templateId = UUID.randomUUID().toString();
        template.put("id", templateId);
        template.put("version", 1);
        template.put("uri", "https://api.notifications.va.gov/templates/" + templateId);
        content.put("template", template);
        content.put("body", "Body of the message");
        content.put("subject", "Subject of the message");
        content.put("created_at", "2016-03-01T08:30:00.000Z");
        content.put("sent_at", "2016-03-01T08:30:03.000Z");
        content.put("completed_at", "2016-03-01T08:30:43.000Z");
        content.put("estimated_delivery", "2016-03-03T16:00:00.000Z");
        content.put("created_by_name", "John Doe");
        content.put("billing_code", "some-billing-code");
        JSONObject recipientIdentifier = new JSONObject();
        recipientIdentifier.put("id_type", IdentifierType.ICN.toString());
        String identifierValue = UUID.randomUUID().toString();
        recipientIdentifier.put("id_value", identifierValue + IdentifierType.ICN.suffix());
        content.put("recipient_identifiers", new JSONArray(asList(recipientIdentifier)));

        Notification notification = new Notification(content.toString());
        assertEquals(UUID.fromString(id), notification.getId());
        assertEquals(Optional.of("client_reference"), notification.getReference());
        assertEquals(UUID.fromString(templateId), notification.getTemplateId());
        assertEquals("email", notification.getNotificationType());
        assertEquals(Optional.of("some@address.com"), notification.getEmailAddress());
        assertEquals(Optional.<String>empty(), notification.getPhoneNumber());
        assertEquals(Optional.<String>empty(), notification.getLine1());
        assertEquals(Optional.<String>empty(), notification.getLine2());
        assertEquals(Optional.<String>empty(), notification.getLine3());
        assertEquals(Optional.<String>empty(), notification.getLine4());
        assertEquals(Optional.<String>empty(), notification.getLine5());
        assertEquals(Optional.<String>empty(), notification.getLine6());
        assertEquals(Optional.<String>empty(), notification.getPostcode());
        assertEquals(Optional.<String>empty(), notification.getPostage());
        assertEquals(UUID.fromString(templateId), notification.getTemplateId());
        assertEquals(1, notification.getTemplateVersion());
        assertEquals("https://api.notifications.va.gov/templates/" + templateId, notification.getTemplateUri());
        assertEquals("Body of the message", notification.getBody());
        assertEquals(Optional.of("Subject of the message"), notification.getSubject());
        assertEquals(new DateTime("2016-03-01T08:30:00.000Z"), notification.getCreatedAt());
        assertEquals(Optional.of(new DateTime("2016-03-01T08:30:03.000Z")), notification.getSentAt());
        assertEquals(Optional.of(new DateTime("2016-03-01T08:30:43.000Z")), notification.getCompletedAt());
        assertEquals(Optional.of(new DateTime("2016-03-03T16:00:00.000Z")), notification.getEstimatedDelivery());
        assertEquals(Optional.of("John Doe"), notification.getCreatedByName());
        assertEquals(Optional.of("some-billing-code"), notification.getBillingCode());
        assertEquals(new Identifier(IdentifierType.ICN, identifierValue), notification.getRecipientIdentifiers().get(0));
    }

    @Test
    public void testSmsNotification_canCreateObjectFromJson() {
        JSONObject content = new JSONObject();
        String id = UUID.randomUUID().toString();
        content.put("id", id);
        content.put("reference", "client_reference");
        content.put("email_address", null);
        content.put("phone_number", "+447111111111");
        content.put("line_1", null);
        content.put("line_2", null);
        content.put("line_3", null);
        content.put("line_4", null);
        content.put("line_5", null);
        content.put("line_6", null);
        content.put("postcode", null);
        content.put("postage", null);
        content.put("type", "sms");
        content.put("status", "delivered");
        JSONObject template = new JSONObject();
        String templateId = UUID.randomUUID().toString();
        template.put("id", templateId);
        template.put("version", 1);
        template.put("uri", "https://api.notifications.va.gov/templates/" + templateId);
        content.put("template", template);
        content.put("body", "Body of the message");
        content.put("subject", null);
        content.put("created_at", "2016-03-01T08:30:00.000Z");
        content.put("sent_at", "2016-03-01T08:30:03.000Z");
        content.put("completed_at", "2016-03-01T08:30:43.000Z");
        content.put("estimated_delivery", "2016-03-03T16:00:00.000Z");
        content.put("created_by_name", "John Doe");
        content.put("billing_code", "some-billing-code");

        String firstIdentifierValue = UUID.randomUUID().toString();
        Identifier firstIdentifier = new Identifier(IdentifierType.PID, firstIdentifierValue);
        String secondIdentifierValue = UUID.randomUUID().toString();
        Identifier secondIdentifier = new Identifier(IdentifierType.VAPROFILEID, secondIdentifierValue);
        content.put("recipient_identifiers", new JSONArray(asList(
                new JSONObject(firstIdentifier.asJson().toMap()),
                new JSONObject(secondIdentifier.asJson().toMap())
        )));

        Notification notification = new Notification(content.toString());
        assertEquals(UUID.fromString(id), notification.getId());
        assertEquals(Optional.of("client_reference"), notification.getReference());
        assertEquals(UUID.fromString(templateId), notification.getTemplateId());
        assertEquals("sms", notification.getNotificationType());

        assertEquals(Optional.of("+447111111111"), notification.getPhoneNumber());
        assertEquals(Optional.<String>empty(), notification.getEmailAddress());
        assertEquals(Optional.<String>empty(), notification.getLine1());
        assertEquals(Optional.<String>empty(), notification.getLine2());
        assertEquals(Optional.<String>empty(), notification.getLine3());
        assertEquals(Optional.<String>empty(), notification.getLine4());
        assertEquals(Optional.<String>empty(), notification.getLine5());
        assertEquals(Optional.<String>empty(), notification.getLine6());
        assertEquals(Optional.<String>empty(), notification.getPostcode());
        assertEquals(Optional.<String>empty(), notification.getPostage());
        assertEquals(UUID.fromString(templateId), notification.getTemplateId());
        assertEquals(1, notification.getTemplateVersion());
        assertEquals("https://api.notifications.va.gov/templates/" + templateId, notification.getTemplateUri());
        assertEquals("Body of the message", notification.getBody());
        assertEquals(Optional.empty(), notification.getSubject());
        assertEquals(new DateTime("2016-03-01T08:30:00.000Z"), notification.getCreatedAt());
        assertEquals(Optional.of(new DateTime("2016-03-01T08:30:03.000Z")), notification.getSentAt());
        assertEquals(Optional.of(new DateTime("2016-03-01T08:30:43.000Z")), notification.getCompletedAt());
        assertEquals(Optional.of(new DateTime("2016-03-03T16:00:00.000Z")), notification.getEstimatedDelivery());
        assertEquals(Optional.of("John Doe"), notification.getCreatedByName());
        assertEquals(Optional.of("some-billing-code"), notification.getBillingCode());
        List<Identifier> actualIdentifiers = notification.getRecipientIdentifiers();
        assertEquals(2, actualIdentifiers.size());
        assertTrue(actualIdentifiers.contains(firstIdentifier));
        assertTrue(actualIdentifiers.contains(secondIdentifier));
    }


    @Test
    public void testLetterNotification_canCreateObjectFromJson() {
        JSONObject content = new JSONObject();
        String id = UUID.randomUUID().toString();
        content.put("id", id);
        content.put("reference", "client_reference");
        content.put("email_address", null);
        content.put("phone_number", null);
        content.put("line_1", "the queen");
        content.put("line_2", "buckingham palace");
        content.put("line_3", null);
        content.put("line_4", null);
        content.put("line_5", null);
        content.put("line_6", null);
        content.put("postcode", "SW1 1AA");
        content.put("postage", "first");
        content.put("type", "letter");
        content.put("status", "delivered");
        JSONObject template = new JSONObject();
        String templateId = UUID.randomUUID().toString();
        template.put("id", templateId);
        template.put("version", 1);
        template.put("uri", "https://api.notifications.va.gov/templates/" + templateId);
        content.put("template", template);
        content.put("body", "Body of the message");
        content.put("subject", null);
        content.put("created_at", "2016-03-01T08:30:00.000Z");
        content.put("sent_at", "2016-03-01T08:30:03.000Z");
        content.put("completed_at", "2016-03-01T08:30:43.000Z");
        content.put("estimated_delivery", "2016-03-03T16:00:00.000Z");
        content.put("created_by_name", "John Doe");

        Notification notification = new Notification(content.toString());
        assertEquals(UUID.fromString(id), notification.getId());
        assertEquals(Optional.of("client_reference"), notification.getReference());
        assertEquals(UUID.fromString(templateId), notification.getTemplateId());
        assertEquals("letter", notification.getNotificationType());

        assertEquals(Optional.<String>empty(), notification.getPhoneNumber());
        assertEquals(Optional.<String>empty(), notification.getEmailAddress());
        assertEquals(Optional.of("the queen"), notification.getLine1());
        assertEquals(Optional.of("buckingham palace"), notification.getLine2());
        assertEquals(Optional.<String>empty(), notification.getLine3());
        assertEquals(Optional.<String>empty(), notification.getLine4());
        assertEquals(Optional.<String>empty(), notification.getLine5());
        assertEquals(Optional.<String>empty(), notification.getLine6());
        assertEquals(Optional.of("SW1 1AA"), notification.getPostcode());
        assertEquals(Optional.of("first"), notification.getPostage());
        assertEquals(UUID.fromString(templateId), notification.getTemplateId());
        assertEquals(1, notification.getTemplateVersion());
        assertEquals("https://api.notifications.va.gov/templates/" + templateId, notification.getTemplateUri());
        assertEquals("Body of the message", notification.getBody());
        assertEquals(Optional.empty(), notification.getSubject());
        assertEquals(new DateTime("2016-03-01T08:30:00.000Z"), notification.getCreatedAt());
        assertEquals(Optional.of(new DateTime("2016-03-01T08:30:03.000Z")), notification.getSentAt());
        assertEquals(Optional.of(new DateTime("2016-03-01T08:30:43.000Z")), notification.getCompletedAt());
        assertEquals(Optional.of(new DateTime("2016-03-03T16:00:00.000Z")), notification.getEstimatedDelivery());
        assertEquals(Optional.of("John Doe"), notification.getCreatedByName());
    }
}
