package gov.va.vanotify;

import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static gov.va.vanotify.GsonConfiguration.gsonInstance;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class SendLetterResponseTest {

    @Test
    public void testNotificationResponseForLetterResponse(){
        JsonObject postLetterResponse = new JsonObject();
        UUID id = UUID.randomUUID();
        postLetterResponse.addProperty("id", id.toString());
        postLetterResponse.addProperty("reference", "clientReference");
        JsonObject template = new JsonObject();
        UUID templateId = UUID.randomUUID();
        template.addProperty("id", templateId.toString());
        template.addProperty("version", 1);
        template.addProperty("uri", "https://api.notifications.va.gov/templates/"+templateId);
        postLetterResponse.add("template", template);
        JsonObject content = new JsonObject();
        content.addProperty("body", "hello Fred");
        content.addProperty("subject", "Reminder for thing");
        postLetterResponse.add("content", content);


        SendLetterResponse response = gsonInstance.fromJson(postLetterResponse.toString(), SendLetterResponse.class);
        assertEquals(id, response.getNotificationId());
        assertEquals(Optional.of("clientReference"), response.getReference());
        assertEquals(templateId, response.getTemplateId());
        assertEquals("https://api.notifications.va.gov/templates/"+templateId, response.getTemplateUri());
        assertEquals(1, response.getTemplateVersion());
        assertEquals("hello Fred", response.getBody());
        assertEquals("Reminder for thing", response.getSubject());
    }

    @Test
    public void testNotificationResponseForPrecompiledLetterResponse(){
        String precompiledPdfResponse = "{\n" +
                "  \"content\": {\n" +
                "    \"body\": null, \n" +
                "    \"subject\": \"Pre-compiled PDF\"\n" +
                "  }, \n" +
                "  \"id\": \"5f88e576-c97a-4262-a74b-f558882ca1c8\", \n" +
                "  \"reference\": \"reference\", \n" +
                "  \"scheduled_for\": null, \n" +
                "  \"template\": {\n" +
                "    \"id\": \"1d7b2fac-bb0d-46c6-96e7-d4afa6e22a92\", \n" +
                "    \"uri\": \"https://api.notify.works/services/service_id/templates/template_id\", \n" +
                "    \"version\": 1\n" +
                "  }, \n" +
                "  \"uri\": \"https://api.notify.works/v2/notifications/notification_id\"\n" +
                "}";

        SendLetterResponse response = gsonInstance.fromJson(precompiledPdfResponse, SendLetterResponse.class);
        assertEquals("5f88e576-c97a-4262-a74b-f558882ca1c8", response.getNotificationId().toString());
        assertEquals(Optional.of("reference"), response.getReference());
        assertEquals("1d7b2fac-bb0d-46c6-96e7-d4afa6e22a92", response.getTemplateId().toString());
        assertEquals("https://api.notify.works/services/service_id/templates/template_id", response.getTemplateUri());
        assertEquals(1, response.getTemplateVersion());
        assertNull(response.getBody());
        assertEquals("Pre-compiled PDF", response.getSubject());
    }
}
