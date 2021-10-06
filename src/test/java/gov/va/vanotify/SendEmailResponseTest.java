package gov.va.vanotify;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static gov.va.vanotify.GsonConfiguration.gsonInstance;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SendEmailResponseTest {

    @Test
    public void testNotificationResponseForEmailResponse(){
        JSONObject postEmailResponse = new JSONObject();
        UUID id = UUID.randomUUID();
        postEmailResponse.put("id", id);
        postEmailResponse.put("reference", "clientReference");
        JSONObject template = new JSONObject();
        UUID templateId = UUID.randomUUID();
        template.put("id", templateId);
        template.put("version", 1);
        template.put("uri", "https://api.notifications.va.gov/templates/"+templateId);
        postEmailResponse.put("template", template);
        JSONObject content = new JSONObject();
        content.put("body", "hello Fred");
        content.put("from_email", "senderId");
        content.put("subject", "Reminder for thing");
        postEmailResponse.put("content", content);
        postEmailResponse.put("billing_code", "custom code");


        SendEmailResponse response = gsonInstance.fromJson(postEmailResponse.toString(), SendEmailResponse.class);
        assertEquals(id, response.getNotificationId());
        assertEquals(Optional.of("clientReference"), response.getReference());
        assertEquals(templateId, response.getTemplateId());
        assertEquals("https://api.notifications.va.gov/templates/"+templateId, response.getTemplateUri());
        assertEquals(1, response.getTemplateVersion());
        assertEquals("hello Fred", response.getBody());
        assertEquals("Reminder for thing", response.getSubject());
        assertEquals(Optional.of("senderId"), response.getFromEmail());
        assertEquals(Optional.of("custom code"), response.getBillingCode());
    }
}
