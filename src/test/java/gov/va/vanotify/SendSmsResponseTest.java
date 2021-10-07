package gov.va.vanotify;

import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static gov.va.vanotify.GsonConfiguration.gsonInstance;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SendSmsResponseTest {

    @Test
    public void testNotificationResponseForSmsResponse(){
        JsonObject postSmsReponse = new JsonObject();
        UUID id = UUID.randomUUID();
        postSmsReponse.addProperty("id", id.toString());
        postSmsReponse.addProperty("reference", "clientReference");
        JsonObject template = new JsonObject();
        UUID templateId = UUID.randomUUID();
        template.addProperty("id", templateId.toString());
        template.addProperty("version", 1);
        template.addProperty("uri", "https://api.notifications.va.gov/templates/"+templateId);
        postSmsReponse.add("template", template);
        JsonObject content = new JsonObject();
        content.addProperty("body", "hello Fred");
        content.addProperty("from_number", "senderId");
        postSmsReponse.add("content", content);
        postSmsReponse.addProperty("billing_code", "custom code");


        SendSmsResponse response = gsonInstance.fromJson(postSmsReponse.toString(), SendSmsResponse.class);
        assertEquals(id, response.getNotificationId());
        assertEquals(Optional.of("clientReference"), response.getReference());
        assertEquals(templateId, response.getTemplateId());
        assertEquals("https://api.notifications.va.gov/templates/"+templateId, response.getTemplateUri());
        assertEquals(1, response.getTemplateVersion());
        assertEquals("hello Fred", response.getBody());
        assertEquals(Optional.of("senderId"), response.getFromNumber());
        assertEquals(Optional.of("custom code"), response.getBillingCode());
    }
}