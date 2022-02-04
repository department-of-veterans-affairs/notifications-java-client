package gov.va.vanotify;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static gov.va.vanotify.GsonConfiguration.gsonInstance;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class NotificationListTest {
    @Test
    public void testNotificationList_canCreateObjectFromJson() {
        JsonObject email = new JsonObject();
        String id = UUID.randomUUID().toString();
        email.addProperty("id", id);
        email.addProperty("reference", "client_reference");
        email.addProperty("email_address", "some@address.com");
        email.add("phone_number", null);
        email.add("line_1", null);
        email.add("line_2", null);
        email.add("line_3", null);
        email.add("line_4", null);
        email.add("line_5", null);
        email.add("line_6", null);
        email.add("postcode", null);
        email.addProperty("type", "email");
        email.addProperty("status", "delivered");
        JsonObject template = new JsonObject();
        String templateId = UUID.randomUUID().toString();
        template.addProperty("id", templateId);
        template.addProperty("version", 1);
        template.addProperty("uri", "https://api.notifications.va.gov/templates/" + templateId);
        email.add("template", template);
        email.addProperty("body", "Body of the message");
        email.addProperty("subject", "Subject of the message");
        email.addProperty("created_at", "2016-03-01T08:30:00.000Z");
        email.addProperty("sent_at", "2016-03-01T08:30:03.000Z");
        email.addProperty("completed_at", "2016-03-01T08:30:43.000Z");

        JsonObject sms = new JsonObject();
        sms.addProperty("id", id);
        sms.addProperty("reference", "client_reference");
        sms.add("email_address", null);
        sms.addProperty("phone_number", "+447111111111");
        sms.add("line_1", null);
        sms.add("line_2", null);
        sms.add("line_3", null);
        sms.add("line_4", null);
        sms.add("line_5", null);
        sms.add("line_6", null);
        sms.add("postcode", null);
        sms.addProperty("type", "email");
        sms.addProperty("status", "delivered");
        template.addProperty("id", templateId);
        template.addProperty("version", 1);
        template.addProperty("uri", "https://api.notifications.va.gov/templates/" + templateId);
        sms.add("template", template);
        sms.addProperty("body", "Body of the message");
        sms.add("subject", null);
        sms.addProperty("created_at", "2016-03-01T08:30:00.000Z");
        sms.addProperty("sent_at", "2016-03-01T08:30:03.000Z");
        sms.addProperty("completed_at", "2016-03-01T08:30:43.000Z");

        JsonObject letter = new JsonObject();
        letter.addProperty("id", id);
        letter.addProperty("reference", "client_reference");
        letter.add("email_address", null);
        letter.add("phone_number", null);
        letter.addProperty("line_1", "the queen");
        letter.addProperty("line_2", "buckingham palace");
        letter.add("line_3", null);
        letter.add("line_4", null);
        letter.add("line_5", null);
        letter.add("line_6", null);
        letter.addProperty("postcode", "SW1 1AA");
        letter.addProperty("postage", "first");
        letter.addProperty("type", "email");
        letter.addProperty("status", "delivered");
        template.addProperty("id", templateId);
        template.addProperty("version", 1);
        template.addProperty("uri", "https://api.notifications.va.gov/templates/" + templateId);
        letter.add("template", template);
        letter.addProperty("body", "Body of the message");
        letter.add("subject", null);
        letter.addProperty("created_at", "2016-03-01T08:30:00.000Z");
        letter.addProperty("sent_at", "2016-03-01T08:30:03.000Z");
        letter.addProperty("completed_at", "2016-03-01T08:30:43.000Z");

        JsonArray listNotifications = new JsonArray();
        listNotifications.add(email);
        listNotifications.add(sms);
        listNotifications.add(letter);
        JsonObject content = new JsonObject();
        content.add("notifications", listNotifications);
        JsonObject links = new JsonObject();
        links.addProperty("current", "https://api.notifications.va.gov/notifications");
        links.add("next", null);
        content.add("links", links);


        NotificationList result = gsonInstance.fromJson(content.toString(), NotificationList.class);
        assertEquals(3, result.getNotifications().size());
        assertEquals("https://api.notifications.va.gov/notifications", result.getCurrentPageLink());
        assertEquals(Optional.<String>empty(), result.getNextPageLink());

    }
}
