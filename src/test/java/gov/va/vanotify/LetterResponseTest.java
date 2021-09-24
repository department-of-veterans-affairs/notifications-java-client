package gov.va.vanotify;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LetterResponseTest {

    @Test
    public void testNotificationResponseForLetterResponse(){
        JSONObject postLetterResponse = new JSONObject();
        UUID id = UUID.randomUUID();
        postLetterResponse.put("id", id);
        postLetterResponse.put("reference", "clientReference");

        LetterResponse response = new LetterResponse(postLetterResponse.toString());
        assertEquals(id, response.getNotificationId());
        assertEquals(Optional.of("clientReference"), response.getReference());
        assertEquals(Optional.empty(), response.getPostage());
    }

    @Test
    public void testNotificationResponseForPrecompiledLetterResponse(){
        String precompiledPdfResponse = "{\n" +
                "  \"id\": \"5f88e576-c97a-4262-a74b-f558882ca1c8\", \n" +
                "  \"reference\": \"reference\", \n" +
                "  \"postage\": \"first\"\n" +
                "}";

        LetterResponse response = new LetterResponse(precompiledPdfResponse);
        assertEquals("5f88e576-c97a-4262-a74b-f558882ca1c8", response.getNotificationId().toString());
        assertEquals(Optional.of("reference"), response.getReference());
        assertEquals(Optional.of("first"), response.getPostage());
    }
}
