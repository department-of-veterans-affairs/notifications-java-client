package gov.va.vanotify;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static gov.va.vanotify.GsonConfiguration.gsonInstance;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SendSmsResponseTest {

    @Test
    public void testNotificationResponseForSmsResponse(){
        JSONObject postSmsReponse = new JSONObject();
        UUID id = UUID.randomUUID();
        postSmsReponse.put("id", id);
        postSmsReponse.put("reference", "clientReference");
        JSONObject template = new JSONObject();
        UUID templateId = UUID.randomUUID();
        template.put("id", templateId);
        template.put("version", 1);
        template.put("uri", "https://api.notifications.va.gov/templates/"+templateId);
        postSmsReponse.put("template", template);
        JSONObject content = new JSONObject();
        content.put("body", "hello Fred");
        content.put("from_number", "senderId");
        postSmsReponse.put("content", content);
        postSmsReponse.put("billing_code", "custom code");


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