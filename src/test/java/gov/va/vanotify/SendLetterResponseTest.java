package gov.va.vanotify;

import org.json.JSONObject;
import org.junit.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class SendLetterResponseTest {

    @Test
    public void testNotificationResponseForLetterResponse(){
        JSONObject postLetterResponse = new JSONObject();
        UUID id = UUID.randomUUID();
        postLetterResponse.put("id", id);
        postLetterResponse.put("reference", "clientReference");
        JSONObject template = new JSONObject();
        UUID templateId = UUID.randomUUID();
        template.put("id", templateId);
        template.put("version", 1);
        template.put("uri", "https://api.notifications.va.gov/templates/"+templateId);
        postLetterResponse.put("template", template);
        JSONObject content = new JSONObject();
        content.put("body", "hello Fred");
        content.put("subject", "Reminder for thing");
        postLetterResponse.put("content", content);


        SendLetterResponse response = new SendLetterResponse(postLetterResponse.toString());
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

        SendLetterResponse response = new SendLetterResponse(precompiledPdfResponse);
        assertEquals("5f88e576-c97a-4262-a74b-f558882ca1c8", response.getNotificationId().toString());
        assertEquals(Optional.of("reference"), response.getReference());
        assertEquals("1d7b2fac-bb0d-46c6-96e7-d4afa6e22a92", response.getTemplateId().toString());
        assertEquals("https://api.notify.works/services/service_id/templates/template_id", response.getTemplateUri());
        assertEquals(1, response.getTemplateVersion());
        assertNull(response.getBody());
        assertEquals("Pre-compiled PDF", response.getSubject());
    }
}
