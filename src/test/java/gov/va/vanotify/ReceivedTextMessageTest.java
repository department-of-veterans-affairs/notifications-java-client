package gov.va.vanotify;

import com.google.gson.JsonObject;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static gov.va.vanotify.GsonConfiguration.gsonInstance;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReceivedTextMessageTest {

    @Test
    public void testReceivedTextMessage_canCreateObjectFromJson(){
        JsonObject content = new JsonObject();
        String id = UUID.randomUUID().toString();
        String serviceId = UUID.randomUUID().toString();
        content.addProperty("id", id);
        content.addProperty("notify_number", "447700900111");
        content.addProperty("user_number", "447700900000");
        content.addProperty("service_id", serviceId);
        content.addProperty("content", "Content of the message from the user");
        content.addProperty("created_at","2016-03-01T08:30:00.000Z");

        ReceivedTextMessage receivedTextMessage = gsonInstance.fromJson(content.toString(), ReceivedTextMessage.class);
        assertEquals(UUID.fromString(id), receivedTextMessage.getId());
        assertEquals("447700900111", receivedTextMessage.getNotifyNumber());
        assertEquals("447700900000", receivedTextMessage.getUserNumber());
        assertEquals(UUID.fromString(serviceId), receivedTextMessage.getServiceId());
        assertEquals("Content of the message from the user", receivedTextMessage.getContent());
        assertEquals(new DateTime("2016-03-01T08:30:00.000Z"), receivedTextMessage.getCreatedAt());
    }
}
