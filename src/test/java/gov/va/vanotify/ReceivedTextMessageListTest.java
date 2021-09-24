package gov.va.vanotify;

import org.jose4j.json.internal.json_simple.JSONArray;
import org.jose4j.json.internal.json_simple.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReceivedTextMessageListTest {

    @Test
    public void testReceivedTextMessageList_canCreateObjectFromJson(){

        JSONObject receivedTextMessage = new JSONObject();
        String id = UUID.randomUUID().toString();
        String serviceId = UUID.randomUUID().toString();
        receivedTextMessage.put("id", id);
        receivedTextMessage.put("notify_number", "447700900111");
        receivedTextMessage.put("user_number", "447700900000");
        receivedTextMessage.put("service_id", serviceId);
        receivedTextMessage.put("content", "message 1");
        receivedTextMessage.put("receivedTextMessage", "Content of the message from the user");
        receivedTextMessage.put("created_at", "2016-03-01T08:30:00.000Z");

        JSONObject receivedTextMessage2 = new JSONObject();
        String id2 = UUID.randomUUID().toString();
        receivedTextMessage2.put("id", id2);
        receivedTextMessage2.put("notify_number", "447700900111");
        receivedTextMessage2.put("user_number", "447700900000");
        receivedTextMessage2.put("service_id", serviceId);
        receivedTextMessage2.put("content", "message 2");
        receivedTextMessage2.put("receivedTextMessage", "Content of the second message");
        receivedTextMessage2.put("created_at", "2016-03-01T08:35:00.000Z");

        JSONArray listReceivedTextMessages = new JSONArray();
        listReceivedTextMessages.add(receivedTextMessage);
        listReceivedTextMessages.add(receivedTextMessage2);

        JSONObject content = new JSONObject();
        content.put("received_text_messages", listReceivedTextMessages);
        JSONObject links = new JSONObject();
        links.put("current", "https://api.notifications.va.gov/received-text-messages");
        links.put("next", null);
        content.put("links", links);

        ReceivedTextMessageList result = new ReceivedTextMessageList(content.toString());
        assertEquals(2, result.getReceivedTextMessages().size());
        assertEquals("https://api.notifications.va.gov/received-text-messages", result.getCurrentPageLink());
        assertEquals(Optional.<String>empty(), result.getNextPageLink());

    }
}
