package gov.va.vanotify;


import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static gov.va.vanotify.GsonConfiguration.gsonInstance;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LetterResponseTest {

    @Test
    public void testNotificationResponseForLetterResponse(){
        JsonObject postLetterResponse = new JsonObject();
        UUID id = UUID.randomUUID();
        postLetterResponse.addProperty("id", id.toString());
        postLetterResponse.addProperty("reference", "clientReference");

        LetterResponse response = gsonInstance.fromJson(postLetterResponse.toString(), LetterResponse.class);
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

        LetterResponse response = gsonInstance.fromJson(precompiledPdfResponse, LetterResponse.class);
        assertEquals("5f88e576-c97a-4262-a74b-f558882ca1c8", response.getNotificationId().toString());
        assertEquals(Optional.of("reference"), response.getReference());
        assertEquals(Optional.of("first"), response.getPostage());
    }
}
