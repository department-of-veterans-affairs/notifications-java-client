package gov.va.vanotify;

import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static gov.va.vanotify.GsonConfiguration.gsonInstance;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SendEmailResponseTest {

    @Test
    public void testNotificationResponseForEmailResponse(){
        JsonObject postEmailResponse = new JsonObject();
        UUID id = UUID.randomUUID();
        postEmailResponse.addProperty("id", id.toString());
        postEmailResponse.addProperty("reference", "clientReference");
        JsonObject template = new JsonObject();
        UUID templateId = UUID.randomUUID();
        template.addProperty("id", templateId.toString());
        template.addProperty("version", 1);
        template.addProperty("uri", "https://api.notifications.va.gov/templates/"+templateId);
        postEmailResponse.add("template", template);
        JsonObject content = new JsonObject();
        content.addProperty("body", "hello Fred");
        content.addProperty("from_email", "senderId");
        content.addProperty("subject", "Reminder for thing");
        postEmailResponse.add("content", content);
        postEmailResponse.addProperty("billing_code", "custom code");


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
