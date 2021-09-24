package gov.va.vanotify;

import org.jose4j.json.internal.json_simple.JSONArray;
import org.jose4j.json.internal.json_simple.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class NotificationListTest {
    @Test
    public void testNotificationList_canCreateObjectFromJson() {
        JSONObject email = new JSONObject();
        String id = UUID.randomUUID().toString();
        email.put("id", id);
        email.put("reference", "client_reference");
        email.put("email_address", "some@address.com");
        email.put("phone_number", null);
        email.put("line_1", null);
        email.put("line_2", null);
        email.put("line_3", null);
        email.put("line_4", null);
        email.put("line_5", null);
        email.put("line_6", null);
        email.put("postcode", null);
        email.put("type", "email");
        email.put("status", "delivered");
        JSONObject template = new JSONObject();
        String templateId = UUID.randomUUID().toString();
        template.put("id", templateId);
        template.put("version", 1);
        template.put("uri", "https://api.notifications.va.gov/templates/" + templateId);
        email.put("template", template);
        email.put("body", "Body of the message");
        email.put("subject", "Subject of the message");
        email.put("created_at", "2016-03-01T08:30:00.000Z");
        email.put("sent_at", "2016-03-01T08:30:03.000Z");
        email.put("completed_at", "2016-03-01T08:30:43.000Z");

        JSONObject sms = new JSONObject();
        sms.put("id", id);
        sms.put("reference", "client_reference");
        sms.put("email_address", null);
        sms.put("phone_number", "+447111111111");
        sms.put("line_1", null);
        sms.put("line_2", null);
        sms.put("line_3", null);
        sms.put("line_4", null);
        sms.put("line_5", null);
        sms.put("line_6", null);
        sms.put("postcode", null);
        sms.put("type", "email");
        sms.put("status", "delivered");
        template.put("id", templateId);
        template.put("version", 1);
        template.put("uri", "https://api.notifications.va.gov/templates/" + templateId);
        sms.put("template", template);
        sms.put("body", "Body of the message");
        sms.put("subject", null);
        sms.put("created_at", "2016-03-01T08:30:00.000Z");
        sms.put("sent_at", "2016-03-01T08:30:03.000Z");
        sms.put("completed_at", "2016-03-01T08:30:43.000Z");

        JSONObject letter = new JSONObject();
        letter.put("id", id);
        letter.put("reference", "client_reference");
        letter.put("email_address", null);
        letter.put("phone_number", null);
        letter.put("line_1", "the queen");
        letter.put("line_2", "buckingham palace");
        letter.put("line_3", null);
        letter.put("line_4", null);
        letter.put("line_5", null);
        letter.put("line_6", null);
        letter.put("postcode", "SW1 1AA");
        letter.put("postage", "first");
        letter.put("type", "email");
        letter.put("status", "delivered");
        template.put("id", templateId);
        template.put("version", 1);
        template.put("uri", "https://api.notifications.va.gov/templates/" + templateId);
        letter.put("template", template);
        letter.put("body", "Body of the message");
        letter.put("subject", null);
        letter.put("created_at", "2016-03-01T08:30:00.000Z");
        letter.put("sent_at", "2016-03-01T08:30:03.000Z");
        letter.put("completed_at", "2016-03-01T08:30:43.000Z");

        JSONArray listNotifications = new JSONArray();
        listNotifications.add(email);
        listNotifications.add(sms);
        listNotifications.add(letter);
        JSONObject content = new JSONObject();
        content.put("notifications", listNotifications);
        JSONObject links = new JSONObject();
        links.put("current", "https://api.notifications.va.gov/notifications");
        links.put("next", null);
        content.put("links", links);


        NotificationList result = new NotificationList(content.toString());
        assertEquals(3, result.getNotifications().size());
        assertEquals("https://api.notifications.va.gov/notifications", result.getCurrentPageLink());
        assertEquals(Optional.<String>empty(), result.getNextPageLink());

    }
}
