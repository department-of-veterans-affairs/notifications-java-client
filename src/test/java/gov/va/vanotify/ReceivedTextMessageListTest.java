package gov.va.vanotify;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static gov.va.vanotify.GsonConfiguration.gsonInstance;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReceivedTextMessageListTest {

    @Test
    public void testReceivedTextMessageList_canCreateObjectFromJson(){

        JsonObject receivedTextMessage = new JsonObject();
        String id = UUID.randomUUID().toString();
        String serviceId = UUID.randomUUID().toString();
        receivedTextMessage.addProperty("id", id);
        receivedTextMessage.addProperty("notify_number", "447700900111");
        receivedTextMessage.addProperty("user_number", "447700900000");
        receivedTextMessage.addProperty("service_id", serviceId);
        receivedTextMessage.addProperty("content", "message 1");
        receivedTextMessage.addProperty("receivedTextMessage", "Content of the message from the user");
        receivedTextMessage.addProperty("created_at", "2016-03-01T08:30:00.000Z");

        JsonObject receivedTextMessage2 = new JsonObject();
        String id2 = UUID.randomUUID().toString();
        receivedTextMessage2.addProperty("id", id2);
        receivedTextMessage2.addProperty("notify_number", "447700900111");
        receivedTextMessage2.addProperty("user_number", "447700900000");
        receivedTextMessage2.addProperty("service_id", serviceId);
        receivedTextMessage2.addProperty("content", "message 2");
        receivedTextMessage2.addProperty("receivedTextMessage", "Content of the second message");
        receivedTextMessage2.addProperty("created_at", "2016-03-01T08:35:00.000Z");

        JsonArray listReceivedTextMessages = new JsonArray();
        listReceivedTextMessages.add(receivedTextMessage);
        listReceivedTextMessages.add(receivedTextMessage2);

        JsonObject content = new JsonObject();
        content.add("received_text_messages", listReceivedTextMessages);
        JsonObject links = new JsonObject();
        links.addProperty("current", "https://api.notifications.va.gov/received-text-messages");
        links.add("next", null);
        content.add("links", links);

        ReceivedTextMessageList result = gsonInstance.fromJson(content.toString(), ReceivedTextMessageList.class);
        assertEquals(2, result.getReceivedTextMessages().size());
        assertEquals("https://api.notifications.va.gov/received-text-messages", result.getCurrentPageLink());
        assertEquals(Optional.<String>empty(), result.getNextPageLink());

    }
}
