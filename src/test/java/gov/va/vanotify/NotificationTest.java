package gov.va.vanotify;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static gov.va.vanotify.GsonConfiguration.gsonInstance;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class NotificationTest {

    @Test
    public void testEmailNotification_canCreateObjectFromJson() {

        //language=JSON
        String json = "{\n" +
                "  \"id\": \"24132799-fe9d-473d-8d4e-e37cb706c70e\",\n" +
                "  \"template\": {\n" +
                "    \"id\": \"64582991-c446-4bc9-b480-b74f84ea41e0\",\n" +
                "    \"version\": 1,\n" +
                "    \"uri\": \"https://api.notifications.va.gov/templates/64582991-c446-4bc9-b480-b74f84ea41e0\"\n" +
                "  },\n" +
                "  \"subject\": \"Subject of the message\",\n" +
                "  \"created_at\": \"2016-03-01T08:30:00.000Z\",\n" +
                "  \"line_1\": null,\n" +
                "  \"line_2\": null,\n" +
                "  \"line_3\": null,\n" +
                "  \"line_4\": null,\n" +
                "  \"line_5\": null,\n" +
                "  \"line_6\": null,\n" +
                "  \"postcode\": null,\n" +
                "  \"type\": \"email\",\n" +
                "  \"body\": \"Body of the message\",\n" +
                "  \"created_by_name\": \"John Doe\",\n" +
                "  \"billing_code\": \"some-billing-code\",\n" +
                "  \"recipient_identifiers\": [\n" +
                "    {\n" +
                "      \"id_type\": \"ICN\",\n" +
                "      \"id_value\": \"3ecfd439-f9f8-417f-babe-161c5bd221b2^NI^200M^USVHA\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"reference\": \"client_reference\",\n" +
                "  \"sent_at\": \"2016-03-01T08:30:03.000Z\",\n" +
                "  \"postage\": null,\n" +
                "  \"completed_at\": \"2016-03-01T08:30:43.000Z\",\n" +
                "  \"email_address\": \"some@address.com\",\n" +
                "  \"phone_number\": null,\n" +
                "  \"status\": \"delivered\"\n" +
                "}";

        Notification notification = gsonInstance.fromJson(json, Notification.class);
        assertEquals(UUID.fromString("24132799-fe9d-473d-8d4e-e37cb706c70e"), notification.getId());
        assertEquals(Optional.of("client_reference"), notification.getReference());
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
        assertEquals(UUID.fromString("64582991-c446-4bc9-b480-b74f84ea41e0"), notification.getTemplateId());
        assertEquals(1, notification.getTemplateVersion());
        assertEquals("https://api.notifications.va.gov/templates/64582991-c446-4bc9-b480-b74f84ea41e0", notification.getTemplateUri());
        assertEquals("Body of the message", notification.getBody());
        assertEquals(Optional.of("Subject of the message"), notification.getSubject());
        assertEquals(new DateTime("2016-03-01T08:30:00.000Z"), notification.getCreatedAt());
        assertEquals(Optional.of(new DateTime("2016-03-01T08:30:03.000Z")), notification.getSentAt());
        assertEquals(Optional.of(new DateTime("2016-03-01T08:30:43.000Z")), notification.getCompletedAt());
        assertEquals(Optional.of("John Doe"), notification.getCreatedByName());
        assertEquals(Optional.of("some-billing-code"), notification.getBillingCode());
        assertEquals(new Identifier(IdentifierType.ICN, "3ecfd439-f9f8-417f-babe-161c5bd221b2^NI^200M^USVHA"), notification.getRecipientIdentifiers().get(0));
    }

    @Test
    public void testSmsNotification_canCreateObjectFromJson() {
        JsonObject content = new JsonObject();
        String id = UUID.randomUUID().toString();
        content.addProperty("id", id);
        content.addProperty("reference", "client_reference");
        content.add("email_address", null);
        content.addProperty("phone_number", "+447111111111");
        content.add("line_1", null);
        content.add("line_2", null);
        content.add("line_3", null);
        content.add("line_4", null);
        content.add("line_5", null);
        content.add("line_6", null);
        content.add("postcode", null);
        content.add("postage", null);
        content.addProperty("type", "sms");
        content.addProperty("status", "delivered");
        JsonObject template = new JsonObject();
        String templateId = UUID.randomUUID().toString();
        template.addProperty("id", templateId);
        template.addProperty("version", 1);
        template.addProperty("uri", "https://api.notifications.va.gov/templates/" + templateId);
        content.add("template", template);
        content.addProperty("body", "Body of the message");
        content.add("subject", null);
        content.addProperty("created_at", "2016-03-01T08:30:00.000Z");
        content.addProperty("sent_at", "2016-03-01T08:30:03.000Z");
        content.addProperty("completed_at", "2016-03-01T08:30:43.000Z");
        content.addProperty("estimated_delivery", "2016-03-03T16:00:00.000Z");
        content.addProperty("created_by_name", "John Doe");
        content.addProperty("billing_code", "some-billing-code");

        String firstIdentifierValue = UUID.randomUUID().toString();
        Identifier firstIdentifier = new Identifier(IdentifierType.PID, firstIdentifierValue);
        String secondIdentifierValue = UUID.randomUUID().toString();
        Identifier secondIdentifier = new Identifier(IdentifierType.VAPROFILEID, secondIdentifierValue);
        JsonArray identifiers = new JsonArray();
        identifiers.add(gsonInstance.toJsonTree(firstIdentifier));
        identifiers.add(gsonInstance.toJsonTree(secondIdentifier));
        content.add("recipient_identifiers", identifiers);

        Notification notification = gsonInstance.fromJson(content.toString(), Notification.class);
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
        JsonObject content = new JsonObject();
        String id = UUID.randomUUID().toString();
        content.addProperty("id", id);
        content.addProperty("reference", "client_reference");
        content.add("email_address", null);
        content.add("phone_number", null);
        content.addProperty("line_1", "the queen");
        content.addProperty("line_2", "buckingham palace");
        content.add("line_3", null);
        content.add("line_4", null);
        content.add("line_5", null);
        content.add("line_6", null);
        content.addProperty("postcode", "SW1 1AA");
        content.addProperty("postage", "first");
        content.addProperty("type", "letter");
        content.addProperty("status", "delivered");
        JsonObject template = new JsonObject();
        String templateId = UUID.randomUUID().toString();
        template.addProperty("id", templateId);
        template.addProperty("version", 1);
        template.addProperty("uri", "https://api.notifications.va.gov/templates/" + templateId);
        content.add("template", template);
        content.addProperty("body", "Body of the message");
        content.add("subject", null);
        content.addProperty("created_at", "2016-03-01T08:30:00.000Z");
        content.addProperty("sent_at", "2016-03-01T08:30:03.000Z");
        content.addProperty("completed_at", "2016-03-01T08:30:43.000Z");
        content.addProperty("estimated_delivery", "2016-03-03T16:00:00.000Z");
        content.addProperty("created_by_name", "John Doe");

        Notification notification = gsonInstance.fromJson(content.toString(), Notification.class);
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
